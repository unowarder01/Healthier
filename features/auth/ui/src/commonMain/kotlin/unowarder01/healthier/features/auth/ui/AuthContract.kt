package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.features.auth.ui.content.SocialProviderUi
import unowarder01.healthier.features.auth.ui.content.getSocialProvidersUi

object AuthContract {
    sealed interface Intent : MVIIntent {
        data class OnSocialProviderClicked(val provider: SocialProviderUi) : Intent
    }

    data class State(
        val socialProviders: List<SocialProviderUi> = getSocialProvidersUi()
    ) : MVIState

    sealed interface Action : MVIAction {
        data object NavigateToCity : Action
    }

    interface Listener {
        fun onSocialProviderClick(provider: SocialProviderUi)
    }
}
