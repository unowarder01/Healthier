package unowarder01.healthier.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import ui.OnboardingComponent
import ui.OnboardingMainScreen
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.auth.ui.AuthMainScreen
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
import unowarder01.healthier.navigation.screens.AppScreensChild.*
import unowarder01.healthier.navigation.screens.AppScreensConfig.AuthConfig
import unowarder01.healthier.navigation.screens.AppScreensConfig.CityConfig
import unowarder01.healthier.navigation.screens.AppScreensConfig.HomeConfig
import unowarder01.healthier.navigation.screens.AppScreensConfig.OnboardingConfig
import unowarder01.healthier.navigation.screens.AppScreensConfig.SplashConfig
import unowarder01.healthier.ui.HomeMainScreen

interface AppScreensNavigator {
    val router: Value<ChildStack<AppScreensConfig, AppScreensChild>>

    @Composable
    fun getContentByChild(child: AppScreensChild)
}

class AppScreensNavigatorImpl(
    context: ComponentContext,
    navigation: StackNavigation<AppScreensConfig>,
    private val koin: Koin,
    private val settings: SettingsRepository
) : AppScreensNavigator, ComponentContext by context {
    /**
     * ROUTER
     */
    override val router = childStack(
        key = "AppScreensNavigator",
        source = navigation,
        serializer = null,
        initialConfiguration = SplashConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    /**
     * CHILDREN
     */
    private fun createChild(config: AppScreensConfig, context: ComponentContext) = when (config) {
        is SplashConfig -> buildSplashChild(context)
        is OnboardingConfig -> buildOnboardingChild(context)
        is AuthConfig -> buildAuthChild(context)
        is CityConfig -> buildCityChild(context)
        is HomeConfig -> buildHomeChild(config, context)

    }

    /**
     * SPLASH
     */
    private fun buildSplashChild(context: ComponentContext) = run {
        val component = koin.get<SplashComponent> { parametersOf(context) }
        SplashChild(component)
    }

    @Composable
    private fun SplashContent(child: SplashChild) {
        val state by child.component.subscribeState()
        SplashMainScreen(
            state = state,
            listener = child.component
        )
    }

    /**
     * ONBOARDING
     */
    private fun buildOnboardingChild(context: ComponentContext) = run {
        val component = koin.get< OnboardingComponent> { parametersOf(context) }
        OnboardingChild(component)
    }

    @Composable
    private fun OnboardingContent(child: OnboardingChild) {
        val state by child.component.subscribeState()
        OnboardingMainScreen(
            state = state,
            listener = child.component
        )
    }

    /**
     * AUTH
     */
    private fun buildAuthChild(context: ComponentContext) = run {
        val component = koin.get<AuthComponent> { parametersOf(context) }
        AuthChild(component)
    }

    @Composable
    private fun AuthContent(
        child: AuthChild,
        language: AppLanguage
    ) {
        val state by child.component.subscribeState()
        AuthMainScreen(
            state = state,
            listener = child.component
        )
    }

    /**
     * CITY
     */
    private fun buildCityChild(context: ComponentContext) = run {
        val component = koin.get<ChooseCityComponent> { parametersOf(context) }
        CityChild(component)
    }

    @Composable
    private fun CityContent(
        child: CityChild,
        language: AppLanguage
    ) {
        val state by child.component.subscribeState()
        ChooseCityMainScreen(
            state = state,
            listener = child.component,
            language = language
        )
    }

    /**
     * HOME
     */
    private fun buildHomeChild(config: HomeConfig, context: ComponentContext) = run {
        val healthComponent = koin.get<HealthComponent>{
            parametersOf(context, config.clinics)
        }
        val mapComponent = koin.get<MapComponent>{
            parametersOf(context, config.clinics)
        }
        val profileComponent = koin.get<ProfileComponent> {
            parametersOf(
                context,
                settings.language.value,
                settings.theme.value
            )
        }
        HomeChild(
            health = healthComponent,
            map = mapComponent,
            profile = profileComponent
        )
    }

    @Composable
    private fun HomeContent(
        child: HomeChild,
        language: AppLanguage
    ) {
        HomeMainScreen(
            language = language,
            health = { HealthContent(child.health, language) },
            map = { MapContent(child.map, language) },
            profile = { ProfileContent(child.profile, language) }
        )
    }

    /**
     * HEALTH
     */
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

    /**
     * MAP
     */
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

    /**
     * PROFILE
     */
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

    /**
     * CONTENT
     */
    @Composable
    override fun getContentByChild(child: AppScreensChild) {
        val language by settings.language.collectAsState()
        when (child) {
            is SplashChild -> SplashContent(child)
            is OnboardingChild -> OnboardingContent(child)
            is AuthChild -> AuthContent(child, language)
            is CityChild -> CityContent(child, language)
            is HomeChild -> HomeContent(child, language)
        }
    }
}

