package unowarder01.healthier.features.map.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.map.ui.MapNavigator
import unowarder01.healthier.features.map.ui.MapViewModel

val mapUiModule = module {
    factoryOf(::MapViewModel)
    factory { (context: ComponentContext) ->
        MapComponent(
            context = context,
            viewModel = get(),
            navigator = get<MapNavigator>(),
        )
    }
}
