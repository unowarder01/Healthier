package unowarder01.healthier.features.profile.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.features.profile.domain.Profile

object ProfileContract {
    enum class Message { ComingSoon, NotConfigured }

    data class State(
        val profile: Profile,
        val language: AppLanguage,
        val theme: AppTheme
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data object RequestProfileEditor : Intent
        data object RequestLanguageSelector : Intent
        data object RequestThemeSelector : Intent
        data class SaveProfile(
            val name: String,
            val avatarReference: String?
        ) : Intent
        data class SelectLanguage(val language: AppLanguage) : Intent
        data class SelectTheme(val theme: AppTheme) : Intent
        data class RequestMessage(val message: Message) : Intent
    }

    sealed interface Action : MVIAction {
        data class ShowProfileEditor(
            val profile: Profile,
            val language: AppLanguage
        ) : Action

        data class ShowLanguageSelector(
            val language: AppLanguage
        ) : Action

        data class ShowThemeSelector(
            val language: AppLanguage,
            val theme: AppTheme
        ) : Action

        data class ShowMessage(
            val language: AppLanguage,
            val message: Message
        ) : Action
    }

    interface Listener {
        fun onLocationChangeRequested()
        fun onLanguageSelectorRequested()
        fun onThemeSelectorRequested()
        fun onEditingStarted()
        fun onUnavailableActionSelected()
        fun onComingSoonActionSelected()
    }
}
