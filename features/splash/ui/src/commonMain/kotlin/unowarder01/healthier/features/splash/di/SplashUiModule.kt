package unowarder01.healthier.features.splash.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import unowarder01.healthier.features.splash.ui.SplashComponent
import unowarder01.healthier.features.splash.ui.SplashViewModel

val splashUiModule = module {
    factory { SplashViewModel(get()) }
    factory { (context: ComponentContext) ->
        SplashComponent(
            context = context,
            viewModel = get(),
            navigator = get()
        )
    }
}
