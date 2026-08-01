package unowarder01.healthier.navigation.screens

import ui.OnboardingComponent
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.splash.ui.SplashComponent

sealed interface AppScreensChild {
    data class SplashChild(val component: SplashComponent) : AppScreensChild
    data class OnboardingChild(val component: OnboardingComponent) : AppScreensChild
    data class AuthChild(val component: AuthComponent) : AppScreensChild
}
