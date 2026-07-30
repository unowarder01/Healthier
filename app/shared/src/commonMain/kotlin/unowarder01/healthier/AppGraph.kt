package unowarder01.healthier

import com.arkivanov.decompose.ComponentContext
import com.russhwolf.settings.Settings
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppResult
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
import unowarder01.healthier.features.auth.di.authFeatureModule
import unowarder01.healthier.features.city.di.cityFeatureModule
import unowarder01.healthier.features.health.di.healthFeatureModule
import unowarder01.healthier.features.map.di.mapFeatureModule
import unowarder01.healthier.features.profile.di.profileFeatureModule
import unowarder01.healthier.features.splash.di.splashFeatureModule
import unowarder01.healthier.navigation.AppScreensNavigator
import unowarder01.healthier.navigation.appNavigationModule
import unowarder01.healthier.navigation.dialogs.AppDialogsNavigator

data class AppRuntimeConfig(
    val isDebug: Boolean,
    val apiBaseUrl: String = ""
)

class AppGraph(
    runtime: AppRuntimeConfig,
    clinicCache: ClinicCache
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
                            AppResult.Failure(AppError.NotConfigured)
                    }
                }
                single<ExternalUrlLauncher> {
                    object : ExternalUrlLauncher {
                        override suspend fun open(url: String): AppResult<Unit> =
                            AppResult.Failure(AppError.NotConfigured)
                    }
                }
                single<MapRenderer> {
                    object : MapRenderer {
                        override val availability = if (runtime.isDebug) {
                            MapAvailability.Demo
                        } else {
                            MapAvailability.Unavailable
                        }
                    }
                }
            },
            splashFeatureModule,
            authFeatureModule,
            cityFeatureModule,
            healthFeatureModule,
            mapFeatureModule,
            profileFeatureModule,
            appNavigationModule
        )
    }

    val koin: Koin
        get() = application.koin
}

class RootComponent(
    context: ComponentContext,
    graph: AppGraph
) : ComponentContext by context {
    val settings: SettingsRepository = graph.koin.get()
    val dialogs: AppDialogsNavigator = graph.koin.get {
        parametersOf(context)
    }
    val navigator: AppScreensNavigator = graph.koin.get {
        parametersOf(context)
    }
}

fun createRootComponent(
    componentContext: ComponentContext,
    runtime: AppRuntimeConfig,
    clinicCache: ClinicCache
): RootComponent = RootComponent(
    context = componentContext,
    graph = AppGraph(runtime, clinicCache)
)
