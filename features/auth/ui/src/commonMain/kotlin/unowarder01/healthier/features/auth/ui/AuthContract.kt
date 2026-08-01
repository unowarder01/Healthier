package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

object AuthContract {
    sealed interface Intent : MVIIntent {
    }

    data class State(
        val visible: Boolean = false
    ) : MVIState

    sealed interface Action : MVIAction {
        data object NavigateToCity : Action
    }

    interface Listener {
    }
}
