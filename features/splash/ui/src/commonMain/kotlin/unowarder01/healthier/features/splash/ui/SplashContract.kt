package unowarder01.healthier.features.splash.ui

import androidx.compose.runtime.Immutable
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.core.common.AppLanguage

object SplashContract {
    sealed interface Intent : MVIIntent {
        data class OnLanguageClicked(val language: AppLanguage) : Intent
    }

    @Immutable
    data class State(
        val languages: List<AppLanguage> = listOf(),
        val selectedLanguage: AppLanguage? = null
    ) : MVIState {
        val showLanguagesContainer get() = languages.isNotEmpty()
    }

    sealed interface Action : MVIAction {
        data object NavigateToAuth : Action
        data object NavigateToOnboarding : Action
    }

    interface Listener {
        fun onLanguageClick(language: AppLanguage)
    }
}
