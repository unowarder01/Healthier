package unowarder01.healthier.navigation.screens.start.root_navigator

import ui.OnboardingComponent
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.city.ui.CityComponent
import unowarder01.healthier.features.splash.ui.SplashComponent

sealed interface StartScreensChild {
    data class SplashChild(val component: SplashComponent) : StartScreensChild
    data class OnboardingChild(val component: OnboardingComponent) : StartScreensChild
    data class AuthChild(val component: AuthComponent) : StartScreensChild
    data class CityChild(val component: CityComponent) : StartScreensChild
}
