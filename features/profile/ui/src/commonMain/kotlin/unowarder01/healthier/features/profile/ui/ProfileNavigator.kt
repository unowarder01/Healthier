package unowarder01.healthier.features.profile.ui

import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.features.profile.domain.Profile
import unowarder01.healthier.features.profile.ui.ProfileContract.Message

interface ProfileNavigator {
    fun changeLocation()

    fun showProfileEditor(
        profile: Profile,
        language: AppLanguage,
        onSave: (name: String, avatarReference: String?) -> Unit
    )

    fun showLanguageSelector(
        language: AppLanguage,
        onSelect: (AppLanguage) -> Unit
    )

    fun showThemeSelector(
        language: AppLanguage,
        theme: AppTheme,
        onSelect: (AppTheme) -> Unit
    )

    fun showMessage(
        language: AppLanguage,
        message: Message
    )
}
