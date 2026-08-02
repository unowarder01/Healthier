package unowarder01.healthier.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.OnboardingNavigator
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.calendar.ui.CalendarNavigator
import unowarder01.healthier.features.city.ui.CityNavigator
import unowarder01.healthier.features.health.ui.HealthNavigator
import unowarder01.healthier.features.map.ui.MapNavigator
import unowarder01.healthier.features.profile.ui.ProfileNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.screens.main.child_navigators.CalendarNavigatorImpl
import unowarder01.healthier.navigation.screens.main.child_navigators.HealthNavigatorImpl
import unowarder01.healthier.navigation.screens.main.child_navigators.MapNavigatorImpl
import unowarder01.healthier.navigation.screens.main.child_navigators.ProfileNavigatorImpl
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensConfig
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensNavigator
import unowarder01.healthier.navigation.screens.main.root_navigator.MainScreensNavigatorImpl
import unowarder01.healthier.navigation.screens.start.child_navigators.AuthNavigatorImpl
import unowarder01.healthier.navigation.screens.start.child_navigators.CityNavigatorImpl
import unowarder01.healthier.navigation.screens.start.child_navigators.OnboardingNavigatorImpl
import unowarder01.healthier.navigation.screens.start.child_navigators.SplashNavigatorImpl
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensConfig
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensNavigator
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensNavigatorImpl

val appNavigatorsModule = module {
    /**
     * START
     */
    single { StackNavigation<StartScreensConfig>() }
    single<StartScreensNavigator> { (context: ComponentContext) ->
        StartScreensNavigatorImpl(
            context = context,
            navigation = get(),
            koin = getKoin()
        )
    }
    factoryOf(::SplashNavigatorImpl) { bind<SplashNavigator>() }
    factoryOf(::OnboardingNavigatorImpl) { bind<OnboardingNavigator>() }
    factoryOf(::AuthNavigatorImpl) { bind<AuthNavigator>() }
    factoryOf(::CityNavigatorImpl) { bind<CityNavigator>() }

    /**
     * MAIN
     */
    single { StackNavigation<MainScreensConfig>() }
    single<MainScreensNavigator> { (context: ComponentContext) ->
        MainScreensNavigatorImpl(
            context = context,
            navigation = get(),
            koin = getKoin()
        )
    }
    factoryOf(::HealthNavigatorImpl) { bind<HealthNavigator>() }
    factoryOf(::MapNavigatorImpl) { bind<MapNavigator>() }
    factoryOf(::CalendarNavigatorImpl) { bind<CalendarNavigator>() }
    factoryOf(::ProfileNavigatorImpl) { bind<ProfileNavigator>() }
}
