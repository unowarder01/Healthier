package unowarder01.healthier.features.health.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

object HealthContract {
    sealed interface Intent : MVIIntent

    data object State : MVIState

    sealed interface Action : MVIAction

    interface Listener
}
