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
        val theme: AppTheme,
        val editing: Boolean = false,
        val draftName: String = profile.name,
        val draftAvatar: String? = profile.avatarReference,
        val showLanguageSelector: Boolean = false,
        val showThemeSelector: Boolean = false,
        val message: Message? = null,
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data object StartEdit : Intent
        data object DismissEdit : Intent
        data class NameChanged(val value: String) : Intent
        data object PickAvatar : Intent
        data object SaveProfile : Intent
        data object ShowLanguageSelector : Intent
        data object ShowThemeSelector : Intent
        data class SelectLanguage(val language: AppLanguage) : Intent
        data class SelectTheme(val theme: AppTheme) : Intent
        data class ShowMessage(val message: Message) : Intent
        data object DismissOverlay : Intent
    }

    sealed interface Action : MVIAction
}
