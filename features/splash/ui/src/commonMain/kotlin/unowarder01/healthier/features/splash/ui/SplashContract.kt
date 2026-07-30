package unowarder01.healthier.features.splash.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.core.common.AppLanguage

object SplashContract {
    data class State(
        val selected: AppLanguage? = null,
        val exiting: Boolean = false
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data class SelectLanguage(val language: AppLanguage) : Intent
    }

    sealed interface Action : MVIAction {
        data object NavigateToAuth : Action
    }

    interface Listener {
        fun onLanguageSelected(language: AppLanguage)
    }
}
