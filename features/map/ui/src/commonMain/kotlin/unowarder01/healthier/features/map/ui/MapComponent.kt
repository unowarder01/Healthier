package unowarder01.healthier.features.map.ui

import com.arkivanov.decompose.ComponentContext
import unowarder01.healthier.core.platform.MapAvailability
import unowarder01.healthier.core.platform.MapRenderer
import unowarder01.healthier.core.presentation.retainedStore
import unowarder01.healthier.features.city.domain.Clinic

class MapComponent(
    componentContext: ComponentContext,
    factory: MapStoreFactory,
    clinics: List<Clinic>,
    renderer: MapRenderer,
    val navigator: MapNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("map.clinics") { factory.create(clinics) }
    val availability: MapAvailability = renderer.availability
}
