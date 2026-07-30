package unowarder01.healthier.features.map.ui

import org.koin.dsl.module
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.presentation.StoreViewModel
import unowarder01.healthier.features.city.domain.Clinic

class MapStoreFactory {
    fun create(clinics: List<Clinic>) =
        healthierStore<MapContract.State, MapContract.Intent, MapContract.Action>(
            name = "map.clinics",
            initial = MapContract.State(clinics),
        ) { intent ->
            when (intent) {
                is MapContract.Intent.SelectClinic ->
                    updateState {
                        copy(selectedClinic = clinics.firstOrNull { it.id == intent.clinicId })
                    }
                MapContract.Intent.DismissClinic ->
                    updateState { copy(selectedClinic = null) }
            }
        }
}

class MapViewModel(factory: MapStoreFactory, clinics: List<Clinic>) :
    StoreViewModel<MapContract.State, MapContract.Intent, MapContract.Action>(
        factory.create(clinics)
    )

val mapUiModule = module {
    factory { MapStoreFactory() }
}
