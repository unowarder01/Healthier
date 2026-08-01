package unowarder01.healthier.navigation.screens

import ui.OnboardingComponent
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.city.ui.ChooseCityComponent
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.profile.ui.ProfileComponent
import unowarder01.healthier.features.splash.ui.SplashComponent

sealed interface AppScreensChild {
    data class SplashChild(val component: SplashComponent) : AppScreensChild
    data class OnboardingChild(val component: OnboardingComponent) : AppScreensChild
    data class AuthChild(val component: AuthComponent) : AppScreensChild
    data class CityChild(val component: ChooseCityComponent) : AppScreensChild
    data class HomeChild(
        val health: HealthComponent,
        val map: MapComponent,
        val profile: ProfileComponent
    ) : AppScreensChild
}