package unowarder01.healthier.features.city.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import unowarder01.healthier.features.city.ui.ChooseCityComponent
import unowarder01.healthier.features.city.ui.ChooseCityViewModel

val cityUiModule = module {
    factory { ChooseCityViewModel(get(), get()) }
    factory { (context: ComponentContext) ->
        ChooseCityComponent(
            context = context,
            viewModel = get(),
            navigator = get()
        )
    }
}
