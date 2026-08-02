package unowarder01.healthier.navigation.screens.main.root_navigator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import org.koin.core.Koin
import unowarder01.healthier.features.calendar.ui.CalendarMainScreen
import unowarder01.healthier.features.health.ui.HealthMainScreen
import unowarder01.healthier.features.map.ui.MapMainScreen
import unowarder01.healthier.features.profile.ui.ProfileMainScreen
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.CalendarChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.HealthChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.MapChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensChild.ProfileChild
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.CalendarConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.HealthConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.MapConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig.ProfileConfig

class MainScreensNavigatorImpl(
    context: ComponentContext,
    navigation: StackNavigation<MainScreensConfig>,
    private val koin: Koin
) : MainScreensNavigator, ComponentContext by context {
    /**
     * ROUTER
     */
    override val router = childStack(
        key = "MainScreensNavigator",
        source = navigation,
        serializer = null,
        initialConfiguration = HealthConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    /**
     * CHILDREN
     */
    private fun createChild(config: MainScreensConfig, context: ComponentContext) = when (config) {
        is HealthConfig -> buildHealthChild(context)
        is MapConfig -> buildMapChild(context)
        is CalendarConfig -> buildCalendarChild(context)
        is ProfileConfig -> buildProfileChild(context)
    }

    /**
     * HEALTH
     */
    private fun buildHealthChild(context: ComponentContext) = run {
        HealthChild
    }
    
    @Composable
    private fun HealthContent(child: HealthChild) {
        HealthMainScreen()
    }

    /**
     * MAP
     */
    private fun buildMapChild(context: ComponentContext) = run {
        MapChild
    }

    @Composable
    private fun MapContent(child: MapChild) {
        MapMainScreen()
    }

    /**
     * CALENDAR
     */
    private fun buildCalendarChild(context: ComponentContext) = run {
        CalendarChild
    }

    @Composable
    private fun CalendarContent(child: CalendarChild) {
        CalendarMainScreen()
    }

    /**
     * PROFILE
     */
    private fun buildProfileChild(context: ComponentContext) = run {
        ProfileChild
    }

    @Composable
    private fun ProfileContent(child: ProfileChild) {
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