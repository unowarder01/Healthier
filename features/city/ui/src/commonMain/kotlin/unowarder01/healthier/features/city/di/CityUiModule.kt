package unowarder01.healthier.features.city.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.city.ui.CityComponent
import unowarder01.healthier.features.city.ui.CityNavigator
import unowarder01.healthier.features.city.ui.CityViewModel

val cityUiModule = module {
    factoryOf(::CityViewModel)
    factory { (context: ComponentContext) ->
        CityComponent(
            context = context,
            viewModel = get(),
            navigator = get<CityNavigator>(),
        )
    }
}
