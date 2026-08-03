package unowarder01.healthier.features.city.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.city.ui.CityComponent
import unowarder01.healthier.features.city.ui.CityNavigator
import unowarder01.healthier.features.city.ui.CityViewModel
import unowarder01.healthier.features.city.ui.mapper.CitiesUiMapper
import unowarder01.healthier.features.city.ui.mapper.CitiesUiMapperImpl
import unowarder01.healthier.features.city.ui.mapper.CityUiMapper
import unowarder01.healthier.features.city.ui.mapper.CityUiMapperImpl

val cityUiModule = module {
    factoryOf(::CityViewModel)
    factoryOf(::CityUiMapperImpl) { bind<CityUiMapper>() }
    factoryOf(::CitiesUiMapperImpl) { bind<CitiesUiMapper>() }
    factory { (context: ComponentContext) ->
        CityComponent(
            context = context,
            viewModel = get<CityViewModel>(),
            navigator = get<CityNavigator>()
        )
    }
}
