package unowarder01.healthier.navigation.screens.start.child_navigators

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import ui.OnboardingNavigator
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.city.ui.CityNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensConfig
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensConfig.AuthConfig
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensConfig.CityConfig
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensConfig.OnboardingConfig

/**
 * SPLASH
 */
class SplashNavigatorImpl(
    private val navigation: StackNavigation<StartScreensConfig>
) : SplashNavigator {
    override fun toAuth() {
        navigation.replaceAll(AuthConfig)
    }

    override fun toOnboarding() {
        navigation.replaceAll(OnboardingConfig)
    }
}

/**
 * ONBOARDING
 */
class OnboardingNavigatorImpl(
    private val navigation: StackNavigation<StartScreensConfig>
): OnboardingNavigator {
    override fun toAuth() {
        navigation.replaceAll(AuthConfig)
    }
}

/**
 * AUTH
 */
class AuthNavigatorImpl(
    private val navigation: StackNavigation<StartScreensConfig>
) : AuthNavigator {
    override fun toCity() {
        navigation.replaceAll(CityConfig)
    }
}

/**
 * CITY
 */
class CityNavigatorImpl : CityNavigator
