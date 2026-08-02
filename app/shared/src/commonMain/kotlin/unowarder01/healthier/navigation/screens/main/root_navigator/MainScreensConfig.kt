package unowarder01.healthier.navigation.screens.main.root_navigator

sealed interface MainScreensConfig {
    data object HealthConfig: MainScreensConfig
    data object MapConfig: MainScreensConfig
    data object CalendarConfig: MainScreensConfig
    data object ProfileConfig: MainScreensConfig
}