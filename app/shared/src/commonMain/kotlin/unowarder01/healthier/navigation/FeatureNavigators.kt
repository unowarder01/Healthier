package unowarder01.healthier.navigation

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import ui.OnboardingNavigator
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.city.ui.CityNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.screens.AppScreensConfig

class SplashNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>
) : SplashNavigator {
    override fun toAuth() {
        navigation.replaceAll(AppScreensConfig.AuthConfig)
    }

    override fun toOnboarding() {
        navigation.replaceAll(AppScreensConfig.OnboardingConfig)
    }
}

class OnboardingNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>
): OnboardingNavigator {
    override fun toAuth() {
        navigation.replaceAll(AppScreensConfig.AuthConfig)
    }
}

class AuthNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>,
) : AuthNavigator {
    override fun toCity() {
        navigation.replaceAll(AppScreensConfig.CityConfig)
    }
}

class CityNavigatorImpl : CityNavigator
