package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.core.platform.SocialProvider

object AuthContract {
    data class State(
        val visible: Boolean = false,
        val loadingProvider: SocialProvider? = null,
        val error: String? = null
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data object Reveal : Intent
        data class Authenticate(val provider: SocialProvider) : Intent
    }

    sealed interface Action : MVIAction {
        data object NavigateToCity : Action
    }

    interface Listener {
        fun onScreenShown()
        fun onProviderSelected(provider: SocialProvider)
    }
}
