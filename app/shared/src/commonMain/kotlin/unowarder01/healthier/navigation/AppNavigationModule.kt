package unowarder01.healthier.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.dsl.module
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.city.ui.ChooseCityNavigator
import unowarder01.healthier.features.health.ui.HealthNavigator
import unowarder01.healthier.features.map.ui.MapNavigator
import unowarder01.healthier.features.profile.ui.ProfileNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.dialogs.AppDialogsNavigator
import unowarder01.healthier.navigation.dialogs.AppDialogsNavigatorImpl

val appNavigationModule = module {
    single { StackNavigation<AppConfig>() }
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
    factory<AuthNavigator> { AuthNavigatorImpl(get()) }
    factory<ChooseCityNavigator> { ChooseCityNavigatorImpl(get()) }
    factory<HealthNavigator> { HealthNavigatorImpl(get(), get()) }
    factory<MapNavigator> { MapNavigatorImpl() }
    factory<ProfileNavigator> { ProfileNavigatorImpl(get(), get()) }
}
