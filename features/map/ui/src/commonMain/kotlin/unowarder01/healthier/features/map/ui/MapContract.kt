package unowarder01.healthier.features.map.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.features.city.domain.Clinic

object MapContract {
    data class State(
        val clinics: List<Clinic>,
        val selectedClinic: Clinic? = null,
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data class SelectClinic(val clinicId: String) : Intent
        data object DismissClinic : Intent
    }

    sealed interface Action : MVIAction
}
