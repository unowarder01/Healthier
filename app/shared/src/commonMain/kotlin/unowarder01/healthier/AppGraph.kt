package unowarder01.healthier

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import org.koin.core.Koin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.database.ClinicCache
import unowarder01.healthier.core.network.NetworkEnvironment
import unowarder01.healthier.core.network.createHttpClient
import unowarder01.healthier.core.network.platformHttpClientEngine
import unowarder01.healthier.core.platform.ExternalUrlLauncher
import unowarder01.healthier.core.platform.MapAvailability
import unowarder01.healthier.core.platform.MapRenderer
import unowarder01.healthier.core.platform.PhotoPicker
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.core.preferences.SettingsRepositoryImpl
import unowarder01.healthier.features.auth.composition.authFeatureModule
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.auth.ui.AuthStoreFactory
import unowarder01.healthier.features.city.composition.cityFeatureModule
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.ui.ChooseCityComponent
import unowarder01.healthier.features.city.ui.ChooseCityNavigator
import unowarder01.healthier.features.city.ui.ChooseCityStoreFactory
import unowarder01.healthier.features.health.composition.healthFeatureModule
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.health.ui.HealthNavigator
import unowarder01.healthier.features.health.ui.HealthStoreFactory
import unowarder01.healthier.features.home.composition.homeFeatureModule
import unowarder01.healthier.features.home.ui.HomeComponent
import unowarder01.healthier.features.home.ui.HomeNavigator
import unowarder01.healthier.features.home.ui.HomeStoreFactory
import unowarder01.healthier.features.map.composition.mapFeatureModule
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.map.ui.MapNavigator
import unowarder01.healthier.features.map.ui.MapStoreFactory
import unowarder01.healthier.features.profile.composition.profileFeatureModule
import unowarder01.healthier.features.profile.ui.ProfileComponent
import unowarder01.healthier.features.profile.ui.ProfileNavigator
import unowarder01.healthier.features.profile.ui.ProfileStoreFactory
import unowarder01.healthier.features.splash.composition.splashFeatureModule
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.features.splash.ui.SplashStoreFactory
import unowarder01.healthier.core.common.AppResult

data class AppRuntimeConfig(
    val isDebug: Boolean,
    val apiBaseUrl: String = "",
)

class AppGraph(
    runtime: AppRuntimeConfig,
    clinicCache: ClinicCache,
) {
    private val application = koinApplication {
        modules(
            module {
                single { NetworkEnvironment(runtime.apiBaseUrl, runtime.isDebug) }
                single { createHttpClient(platformHttpClientEngine(), get()) }
                single<ClinicCache> { clinicCache }
                single<SettingsRepository> { SettingsRepositoryImpl(Settings()) }
                single<PhotoPicker> {
                    object : PhotoPicker {
                        override suspend fun pickAvatar(): AppResult<String> =
                            AppResult.Failure(unowarder01.healthier.core.common.AppError.NotConfigured)
                    }
                }
                single<ExternalUrlLauncher> {
                    object : ExternalUrlLauncher {
                        override suspend fun open(url: String): AppResult<Unit> =
                            AppResult.Failure(unowarder01.healthier.core.common.AppError.NotConfigured)
                    }
                }
                single<MapRenderer> {
                    object : MapRenderer {
                        override val availability =
                            if (runtime.isDebug) MapAvailability.Demo else MapAvailability.Unavailable
                    }
                }
            },
            splashFeatureModule,
            authFeatureModule,
            cityFeatureModule,
            homeFeatureModule,
            healthFeatureModule,
            mapFeatureModule,
            profileFeatureModule,
        )
    }

    val koin: Koin get() = application.koin
}

class RootComponent(
    componentContext: ComponentContext,
    private val graph: AppGraph,
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    val settings: SettingsRepository = graph.koin.get()

    val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        serializer = null,
        initialConfiguration = Config.Splash,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(config: Config, context: ComponentContext): Child = when (config) {
        Config.Splash -> Child.Splash(
            SplashComponent(
                context,
                graph.koin.get<SplashStoreFactory>(),
                SplashNavigator { navigation.replaceCurrent(Config.Auth) },
            )
        )
        Config.Auth -> Child.Auth(
            AuthComponent(
                context,
                graph.koin.get<AuthStoreFactory>(),
                AuthNavigator { navigation.replaceCurrent(Config.City) },
            )
        )
        Config.City -> Child.City(
            ChooseCityComponent(
                context,
                graph.koin.get<ChooseCityStoreFactory>(),
                ChooseCityNavigator { clinics ->
                    navigation.replaceAll(Config.Home(clinics))
                },
            )
        )
        is Config.Home -> createHome(context, config.clinics)
    }

    private fun createHome(context: ComponentContext, clinics: List<Clinic>): Child.Home {
        val openCity = { navigation.pushNew(Config.City) }
        val cycleLanguage = {
            val entries = AppLanguage.entries
            val current = settings.language.value
            settings.setLanguage(entries[(entries.indexOf(current) + 1) % entries.size])
        }
        return Child.Home(
            home = HomeComponent(
                context,
                graph.koin.get<HomeStoreFactory>(),
                object : HomeNavigator {
                    override fun systemBack() = Unit
                },
            ),
            health = HealthComponent(
                context,
                graph.koin.get<HealthStoreFactory>(),
                clinics,
                object : HealthNavigator {
                    override fun changeLocation() = openCity()
                    override fun changeLanguage() = cycleLanguage()
                },
            ),
            map = MapComponent(
                context,
                graph.koin.get<MapStoreFactory>(),
                clinics,
                graph.koin.get(),
                object : MapNavigator {},
            ),
            profile = ProfileComponent(
                context,
                graph.koin.get<ProfileStoreFactory>(),
                settings.language.value,
                settings.theme.value,
                ProfileNavigator { openCity() },
            ),
        )
    }

    sealed interface Config {
        data object Splash : Config
        data object Auth : Config
        data object City : Config
        data class Home(val clinics: List<Clinic>) : Config
    }

    sealed interface Child {
        data class Splash(val component: SplashComponent) : Child
        data class Auth(val component: AuthComponent) : Child
        data class City(val component: ChooseCityComponent) : Child
        data class Home(
            val home: HomeComponent,
            val health: HealthComponent,
            val map: MapComponent,
            val profile: ProfileComponent,
        ) : Child
    }
}

fun createRootComponent(
    componentContext: ComponentContext,
    runtime: AppRuntimeConfig,
    clinicCache: ClinicCache,
): RootComponent = RootComponent(componentContext, AppGraph(runtime, clinicCache))
