package unowarder01.healthier.navigation.screens.main.root_navigator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import unowarder01.healthier.features.calendar.ui.CalendarComponent
import unowarder01.healthier.features.calendar.ui.CalendarMainScreen
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.health.ui.HealthMainScreen
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.map.ui.MapMainScreen
import unowarder01.healthier.features.profile.ui.ProfileComponent
import unowarder01.healthier.features.profile.ui.ProfileMainScreen
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.CalendarChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.HealthChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.MapChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.ProfileChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.CalendarConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.HealthConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.MapConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.MedicalCardConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.ProfileConfig

class MainScreensNavigatorImpl(
    context: ComponentContext,
    private val koin: Koin,
) : MainScreensNavigator, ComponentContext by context {
    /**
     * PAGES
     */
    private val navigation = PagesNavigation<MainScreensConfig>()

    override val pages = childPages(
        key = "MainScreensNavigator",
        source = navigation,
        serializer = MainScreensConfig.serializer(),
        initialPages = { Pages(items = childPagesItems, selectedIndex = 0) },
        childFactory = ::createPage,
    )

    override fun selectPage(index: Int) {
        if (pages.value.selectedIndex != index) {
            navigation.select(index)
        }
    }

    /**
     * CHILDREN
     */
    private fun createPage(config: MainScreensConfig, context: ComponentContext) = when (config) {
        is HealthConfig -> buildHealthChild(context)
        is MapConfig -> buildMapChild(context)
        is CalendarConfig -> buildCalendarChild(context)
        is MedicalCardConfig -> buildCalendarChild(context) // TODO: Add new feature module
        is ProfileConfig -> buildProfileChild(context)
    }

    /**
     * HEALTH
     */
    private fun buildHealthChild(context: ComponentContext) = HealthChild(
        component = koin.get<HealthComponent> { parametersOf(context) }
    )

    @Composable
    private fun HealthContent(child: HealthChild) {
        child.component.subscribeState()
        HealthMainScreen()
    }

    /**
     * MAP
     */
    private fun buildMapChild(context: ComponentContext) = MapChild(
        component = koin.get<MapComponent> { parametersOf(context) }
    )

    @Composable
    private fun MapContent(child: MapChild) {
        child.component.subscribeState()
        MapMainScreen()
    }

    /**
     * CALENDAR
     */
    private fun buildCalendarChild(context: ComponentContext) = CalendarChild(
        component = koin.get<CalendarComponent> { parametersOf(context) }
    )

    @Composable
    private fun CalendarContent(child: CalendarChild) {
        child.component.subscribeState()
        CalendarMainScreen()
    }

    /**
     * PROFILE
     */
    private fun buildProfileChild(context: ComponentContext) = ProfileChild(
        component = koin.get<ProfileComponent> { parametersOf(context) }
    )

    @Composable
    private fun ProfileContent(child: ProfileChild) {
        child.component.subscribeState()
        ProfileMainScreen()
    }

    /**
     * CONTENT
     */
    @Composable
    override fun getContentByChild(child: MainScreensChild) = when (child) {
        is CalendarChild -> CalendarContent(child)
        is HealthChild -> HealthContent(child)
        is MapChild -> MapContent(child)
        is ProfileChild -> ProfileContent(child)
    }
}