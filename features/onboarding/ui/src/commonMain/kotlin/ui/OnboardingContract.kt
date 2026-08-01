package ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

object OnboardingContract {
    sealed interface Intent: MVIIntent {

    }

    data class State(
        val x: Int = 0
    ): MVIState

    sealed interface Action: MVIAction {

    }

    interface Listener {

    }
}