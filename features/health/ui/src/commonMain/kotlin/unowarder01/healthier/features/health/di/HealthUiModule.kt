package unowarder01.healthier.features.health.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.health.ui.HealthViewModel

val healthUiModule = module {
    factory { (clinics: List<Clinic>) -> HealthViewModel(get(), clinics) }
    factory { (context: ComponentContext, clinics: List<Clinic>) ->
        HealthComponent(
            context = context,
            viewModel = get { org.koin.core.parameter.parametersOf(clinics) },
            navigator = get()
        )
    }
}
