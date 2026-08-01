package unowarder01.healthier.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.OnboardingNavigator
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.city.ui.ChooseCityNavigator
import unowarder01.healthier.features.health.ui.HealthNavigator
import unowarder01.healthier.features.map.ui.MapNavigator
import unowarder01.healthier.features.profile.ui.ProfileNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.dialogs.AppDialogsNavigator
import unowarder01.healthier.navigation.dialogs.AppDialogsNavigatorImpl
import unowarder01.healthier.navigation.screens.AppScreensConfig
import unowarder01.healthier.navigation.screens.AppScreensNavigator
import unowarder01.healthier.navigation.screens.AppScreensNavigatorImpl

val appNavigationModule = module {
    single { StackNavigation<AppScreensConfig>() }
    single<AppDialogsNavigator> { (context: ComponentContext) ->
        AppDialogsNavigatorImpl(
            context = context,
            photoPicker = get()
        )
    }
    single<AppScreensNavigator> { (context: ComponentContext) ->
        AppScreensNavigatorImpl(
            context = context,
            navigation = get(),
            koin = getKoin(),
            settings = get()
        )
    }
    factory<SplashNavigator> { SplashNavigatorImpl(get()) }
    factoryOf(::OnboardingNavigatorImpl) { bind<OnboardingNavigator>() }
    factory<AuthNavigator> { AuthNavigatorImpl(get()) }
    factory<ChooseCityNavigator> { ChooseCityNavigatorImpl(get()) }
    factory<HealthNavigator> { HealthNavigatorImpl(get(), get()) }
    factory<MapNavigator> { MapNavigatorImpl() }
    factory<ProfileNavigator> { ProfileNavigatorImpl(get(), get()) }
}
