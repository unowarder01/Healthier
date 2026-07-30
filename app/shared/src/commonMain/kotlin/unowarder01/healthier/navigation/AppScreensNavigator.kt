package unowarder01.healthier.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.auth.ui.AuthMainScreen
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.ui.ChooseCityComponent
import unowarder01.healthier.features.city.ui.ChooseCityMainScreen
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.health.ui.HealthMainScreen
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.map.ui.MapMainScreen
import unowarder01.healthier.features.profile.ui.ProfileComponent
import unowarder01.healthier.features.profile.ui.ProfileMainScreen
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.features.splash.ui.SplashMainScreen
import unowarder01.healthier.ui.HomeMainScreen

interface AppScreensNavigator {
    val router: Value<ChildStack<AppConfig, AppChild>>

    fun toSplashScreen()
    fun toAuthScreen()
    fun toCityScreen()
    fun toHomeScreen(clinics: List<Clinic>)

    @Composable
    fun getContentByChild(child: AppChild)
}

class AppScreensNavigatorImpl(
    context: ComponentContext,
    private val navigation: StackNavigation<AppConfig>,
    private val koin: Koin,
    private val settings: SettingsRepository
) : AppScreensNavigator,
    ComponentContext by context {
    override val router = childStack(
        key = "AppScreensNavigator",
        source = navigation,
        serializer = null,
        initialConfiguration = AppConfig.Splash,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun toSplashScreen() {
        navigation.replaceAll(AppConfig.Splash)
    }

    override fun toAuthScreen() {
        navigation.replaceCurrent(AppConfig.Auth)
    }

    override fun toCityScreen() {
        navigation.replaceCurrent(AppConfig.City)
    }

    override fun toHomeScreen(clinics: List<Clinic>) {
        navigation.replaceAll(AppConfig.Home(clinics))
    }

    private fun createChild(
        config: AppConfig,
        context: ComponentContext
    ): AppChild = when (config) {
        AppConfig.Splash -> AppChild.Splash(
            component = koin.get {
                parametersOf(context)
            }
        )
        AppConfig.Auth -> AppChild.Auth(
            component = koin.get {
                parametersOf(context)
            }
        )
        AppConfig.City -> AppChild.City(
            component = koin.get {
                parametersOf(context)
            }
        )
        is AppConfig.Home -> AppChild.Home(
            health = koin.get {
                parametersOf(context, config.clinics)
            },
            map = koin.get {
                parametersOf(context, config.clinics)
            },
            profile = koin.get {
                parametersOf(
                    context,
                    settings.language.value,
                    settings.theme.value
                )
            }
        )
    }

    @Composable
    override fun getContentByChild(child: AppChild) {
        val language by settings.language.collectAsState()

        when (child) {
            is AppChild.Splash -> SplashContent(child)
            is AppChild.Auth -> AuthContent(child, language)
            is AppChild.City -> CityContent(child, language)
            is AppChild.Home -> HomeContent(child, language)
        }
    }

    @Composable
    private fun SplashContent(child: AppChild.Splash) {
        val state by child.component.subscribeState()
        SplashMainScreen(
            state = state,
            listener = child.component
        )
    }

    @Composable
    private fun AuthContent(
        child: AppChild.Auth,
        language: AppLanguage
    ) {
        val state by child.component.subscribeState()
        AuthMainScreen(
            state = state,
            listener = child.component,
            providers = child.component.providers,
            language = language
        )
    }

    @Composable
    private fun CityContent(
        child: AppChild.City,
        language: AppLanguage
    ) {
        val state by child.component.subscribeState()
        ChooseCityMainScreen(
            state = state,
            listener = child.component,
            language = language
        )
    }

    @Composable
    private fun HomeContent(
        child: AppChild.Home,
        language: AppLanguage
    ) {
        HomeMainScreen(
            language = language,
            health = { HealthContent(child.health, language) },
            map = { MapContent(child.map, language) },
            profile = { ProfileContent(child.profile, language) }
        )
    }

    @Composable
    private fun HealthContent(
        component: HealthComponent,
        language: AppLanguage
    ) {
        val state by component.subscribeState()
        HealthMainScreen(
            state = state,
            listener = component,
            language = language
        )
    }

    @Composable
    private fun MapContent(
        component: MapComponent,
        language: AppLanguage
    ) {
        val state by component.subscribeState()
        MapMainScreen(
            state = state,
            availability = component.availability,
            listener = component,
            language = language
        )
    }

    @Composable
    private fun ProfileContent(
        component: ProfileComponent,
        language: AppLanguage
    ) {
        val state by component.subscribeState()
        ProfileMainScreen(
            state = state.copy(language = language),
            listener = component
        )
    }
}

sealed interface AppConfig {
    data object Splash : AppConfig
    data object Auth : AppConfig
    data object City : AppConfig
    data class Home(val clinics: List<Clinic>) : AppConfig
}

sealed interface AppChild {
    data class Splash(val component: SplashComponent) : AppChild
    data class Auth(val component: AuthComponent) : AppChild
    data class City(val component: ChooseCityComponent) : AppChild
    data class Home(
        val health: HealthComponent,
        val map: MapComponent,
        val profile: ProfileComponent
    ) : AppChild
}
