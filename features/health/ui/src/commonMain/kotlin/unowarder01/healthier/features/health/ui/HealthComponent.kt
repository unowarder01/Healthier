package unowarder01.healthier.features.health.ui

import com.arkivanov.decompose.ComponentContext
import unowarder01.healthier.core.presentation.retainedStore
import unowarder01.healthier.features.city.domain.Clinic

class HealthComponent(
    componentContext: ComponentContext,
    factory: HealthStoreFactory,
    clinics: List<Clinic>,
    val navigator: HealthNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("health.overview") { factory.create(clinics) }
}
