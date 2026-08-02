package unowarder01.healthier.features.profile.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

object ProfileContract {
    sealed interface Intent : MVIIntent

    data object State : MVIState

    sealed interface Action : MVIAction

    interface Listener
}
