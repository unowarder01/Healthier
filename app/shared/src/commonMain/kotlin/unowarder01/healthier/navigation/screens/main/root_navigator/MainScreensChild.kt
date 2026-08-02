package unowarder01.healthier.navigation.screens.main.root_navigator

import unowarder01.healthier.features.calendar.ui.CalendarComponent
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.profile.ui.ProfileComponent

sealed interface MainScreensChild {
    data class HealthChild(val component: HealthComponent) : MainScreensChild
    data class MapChild(val component: MapComponent) : MainScreensChild
    data class CalendarChild(val component: CalendarComponent) : MainScreensChild
    data class ProfileChild(val component: ProfileComponent) : MainScreensChild
}
