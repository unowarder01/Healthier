package unowarder01.healthier.navigation.screens.start.root_navigator

sealed interface StartScreensConfig {
    data object SplashConfig : StartScreensConfig
    data object OnboardingConfig : StartScreensConfig
    data object AuthConfig : StartScreensConfig
    data object CityConfig : StartScreensConfig
}
