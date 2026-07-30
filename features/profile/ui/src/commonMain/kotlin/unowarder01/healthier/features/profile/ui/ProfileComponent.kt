package unowarder01.healthier.features.profile.ui

import com.arkivanov.decompose.ComponentContext
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.presentation.retainedStore

class ProfileComponent(
    componentContext: ComponentContext,
    factory: ProfileStoreFactory,
    language: AppLanguage,
    theme: AppTheme,
    val navigator: ProfileNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("profile.overview") { factory.create(language, theme) }
}
