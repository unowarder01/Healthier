package unowarder01.healthier.navigation.screens.start.root_navigator

import ui.OnboardingComponent
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.city.ui.CityComponent
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensNavigator

sealed interface StartScreensChild {
    data class SplashChild(val component: SplashComponent) : StartScreensChild
    data class OnboardingChild(val component: OnboardingComponent) : StartScreensChild
    data class AuthChild(val component: AuthComponent) : StartScreensChild
    data class CityChild(val component: CityComponent) : StartScreensChild
    data class MainChild(val navigator: MainScreensNavigator) : StartScreensChild
}
