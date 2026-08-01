package unowarder01.healthier.navigation.screens

sealed interface AppScreensConfig {
    data object SplashConfig : AppScreensConfig
    data object OnboardingConfig : AppScreensConfig
    data object AuthConfig : AppScreensConfig
}
