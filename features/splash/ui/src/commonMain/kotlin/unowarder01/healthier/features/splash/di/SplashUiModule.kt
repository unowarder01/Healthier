package unowarder01.healthier.features.splash.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.features.splash.ui.SplashViewModel

val splashUiModule = module {
    factoryOf(::SplashViewModel)
    factory { (context: ComponentContext) ->
        SplashComponent(
            context = context,
            viewModel = get<SplashViewModel>(),
            navigator = get<SplashNavigator>()
        )
    }
}
