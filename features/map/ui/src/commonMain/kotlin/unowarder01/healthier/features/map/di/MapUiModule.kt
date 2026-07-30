package unowarder01.healthier.features.map.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.map.ui.MapComponent
import unowarder01.healthier.features.map.ui.MapViewModel

val mapUiModule = module {
    factory { (clinics: List<Clinic>) -> MapViewModel(clinics) }
    factory { (context: ComponentContext, clinics: List<Clinic>) ->
        MapComponent(
            context = context,
            viewModel = get { org.koin.core.parameter.parametersOf(clinics) },
            renderer = get(),
            navigator = get()
        )
    }
}
