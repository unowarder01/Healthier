package unowarder01.healthier.navigation.screens.start.root_navigator

import kotlinx.serialization.Serializable

@Serializable
sealed interface StartScreensConfig {
    @Serializable
    data object SplashConfig : StartScreensConfig

    @Serializable
    data object OnboardingConfig : StartScreensConfig

    @Serializable
    data object AuthConfig : StartScreensConfig

    @Serializable
    data object CityConfig : StartScreensConfig

    @Serializable
    data object MainConfig : StartScreensConfig
}
