package unowarder01.healthier.navigation.screens.main.root_navigator

import kotlinx.serialization.Serializable
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.CalendarConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.HealthConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.MapConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.MedicalCardConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.ProfileConfig

@Serializable
sealed interface MainScreensConfig {
    @Serializable
    data object HealthConfig : MainScreensConfig

    @Serializable
    data object MapConfig : MainScreensConfig

    @Serializable
    data object CalendarConfig : MainScreensConfig

    @Serializable
    data object MedicalCardConfig : MainScreensConfig

    @Serializable
    data object ProfileConfig : MainScreensConfig
}

internal val childPagesItems = listOf(
    HealthConfig,
    MapConfig,
    CalendarConfig,
    MedicalCardConfig,
    ProfileConfig
)