package unowarder01.healthier.navigation.screens

import unowarder01.healthier.features.city.domain.Clinic

sealed interface AppScreensConfig {
    data object SplashConfig : AppScreensConfig
    data object OnboardingConfig : AppScreensConfig
    data object AuthConfig : AppScreensConfig
    data object CityConfig : AppScreensConfig
    data class HomeConfig(val clinics: List<Clinic>) : AppScreensConfig
}