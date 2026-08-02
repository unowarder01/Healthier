package unowarder01.healthier.navigation.screens.main.root_navigator

sealed interface MainScreensChild {
    data object HealthChild: MainScreensChild
    data object MapChild: MainScreensChild
    data object CalendarChild: MainScreensChild
    data object ProfileChild: MainScreensChild
}