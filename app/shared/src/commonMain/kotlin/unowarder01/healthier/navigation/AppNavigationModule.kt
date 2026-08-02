package unowarder01.healthier.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.OnboardingNavigator
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.city.ui.CityNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.screens.AppScreensConfig
import unowarder01.healthier.navigation.screens.AppScreensNavigator
import unowarder01.healthier.navigation.screens.AppScreensNavigatorImpl

val appNavigationModule = module {
    single { StackNavigation<AppScreensConfig>() }
    single<AppScreensNavigator> { (context: ComponentContext) ->
        AppScreensNavigatorImpl(
            context = context,
            navigation = get(),
            koin = getKoin()
        )
    }
    factoryOf(::SplashNavigatorImpl) { bind<SplashNavigator>() }
    factoryOf(::OnboardingNavigatorImpl) { bind<OnboardingNavigator>() }
    factoryOf(::AuthNavigatorImpl) { bind<AuthNavigator>() }
    factoryOf(::CityNavigatorImpl) { bind<CityNavigator>() }
}
