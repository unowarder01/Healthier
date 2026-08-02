package unowarder01.healthier.navigation.screens.start.root_navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import ui.OnboardingComponent
import ui.OnboardingMainScreen
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.auth.ui.AuthMainScreen
import unowarder01.healthier.features.city.ui.CityComponent
import unowarder01.healthier.features.city.ui.CityMainScreen
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.features.splash.ui.SplashMainScreen
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensChild.AuthChild
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensChild.CityChild
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensChild.OnboardingChild
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensChild.SplashChild

class StartScreensNavigatorImpl(
    context: ComponentContext,
    navigation: StackNavigation<StartScreensConfig>,
    private val koin: Koin
) : StartScreensNavigator, ComponentContext by context {
    /**
     * ROUTER
     */
    override val router = childStack(
        key = "StartScreensNavigator",
        source = navigation,
        serializer = null,
        initialConfiguration = StartScreensConfig.SplashConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    /**
     * CHILDREN
     */
    private fun createChild(config: StartScreensConfig, context: ComponentContext) = when (config) {
        is StartScreensConfig.SplashConfig -> buildSplashChild(context)
        is StartScreensConfig.OnboardingConfig -> buildOnboardingChild(context)
        is StartScreensConfig.AuthConfig -> buildAuthChild(context)
        is StartScreensConfig.CityConfig -> buildCityChild(context)
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
        val component = koin.get<OnboardingComponent> { parametersOf(context) }
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
    private fun AuthContent(child: AuthChild) {
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
        val component = koin.get<CityComponent> { parametersOf(context) }
        CityChild(component)
    }

    @Composable
    private fun CityContent(child: CityChild) {
        child.component.subscribeState()
        CityMainScreen()
    }

    /**
     * CONTENT
     */
    @Composable
    override fun getContentByChild(child: StartScreensChild) {
        when (child) {
            is SplashChild -> SplashContent(child)
            is OnboardingChild -> OnboardingContent(child)
            is AuthChild -> AuthContent(child)
            is CityChild -> CityContent(child)
        }
    }
}