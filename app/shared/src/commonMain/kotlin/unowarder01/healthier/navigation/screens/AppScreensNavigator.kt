package unowarder01.healthier.navigation.screens

import androidx.compose.runtime.Composable
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
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.auth.ui.AuthMainScreen
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.features.splash.ui.SplashMainScreen
import unowarder01.healthier.navigation.screens.AppScreensChild.AuthChild
import unowarder01.healthier.navigation.screens.AppScreensChild.OnboardingChild
import unowarder01.healthier.navigation.screens.AppScreensChild.SplashChild
import unowarder01.healthier.navigation.screens.AppScreensConfig.AuthConfig
import unowarder01.healthier.navigation.screens.AppScreensConfig.OnboardingConfig
import unowarder01.healthier.navigation.screens.AppScreensConfig.SplashConfig

interface AppScreensNavigator {
    val router: Value<ChildStack<AppScreensConfig, AppScreensChild>>

    @Composable
    fun getContentByChild(child: AppScreensChild)
}

class AppScreensNavigatorImpl(
    context: ComponentContext,
    navigation: StackNavigation<AppScreensConfig>,
    private val koin: Koin
) : AppScreensNavigator, ComponentContext by context {
    override val router = childStack(
        key = "AppScreensNavigator",
        source = navigation,
        serializer = null,
        initialConfiguration = SplashConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: AppScreensConfig,
        context: ComponentContext
    ): AppScreensChild = when (config) {
        SplashConfig -> SplashChild(koin.get<SplashComponent> { parametersOf(context) })
        OnboardingConfig -> OnboardingChild(koin.get<OnboardingComponent> { parametersOf(context) })
        AuthConfig -> AuthChild(koin.get<AuthComponent> { parametersOf(context) })
    }

    @Composable
    override fun getContentByChild(child: AppScreensChild) {
        when (child) {
            is SplashChild -> {
                val state by child.component.subscribeState()
                SplashMainScreen(
                    state = state,
                    listener = child.component
                )
            }
            is OnboardingChild -> {
                val state by child.component.subscribeState()
                OnboardingMainScreen(
                    state = state,
                    listener = child.component
                )
            }
            is AuthChild -> {
                val state by child.component.subscribeState()
                AuthMainScreen(
                    state = state,
                    listener = child.component
                )
            }
        }
    }
}
