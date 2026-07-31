package unowarder01.healthier.features.map.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.map.ui.MapContract.Action
import unowarder01.healthier.features.map.ui.MapContract.Intent
import unowarder01.healthier.features.map.ui.MapContract.Intent.DismissClinic
import unowarder01.healthier.features.map.ui.MapContract.Intent.SelectClinic
import unowarder01.healthier.features.map.ui.MapContract.State

class MapViewModel(
    clinics: List<Clinic>
) : BaseViewModel<State, Intent, Action>(
    initialState = State(clinics)
) {
    override suspend fun PipelineContext<State, Intent, Action>.handleIntent(intent: Intent) {
        when (intent) {
            is SelectClinic ->
                updateState {
                    copy(selectedClinic = clinics.firstOrNull { it.id == intent.clinicId })
                }
            DismissClinic ->
                updateState { copy(selectedClinic = null) }
        }
    }
}
