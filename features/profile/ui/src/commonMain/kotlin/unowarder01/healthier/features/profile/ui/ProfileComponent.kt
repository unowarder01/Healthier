package unowarder01.healthier.features.profile.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseFeatureComponent
import unowarder01.healthier.features.profile.ui.ProfileContract.Action
import unowarder01.healthier.features.profile.ui.ProfileContract.Action.ShowLanguageSelector
import unowarder01.healthier.features.profile.ui.ProfileContract.Action.ShowMessage
import unowarder01.healthier.features.profile.ui.ProfileContract.Action.ShowProfileEditor
import unowarder01.healthier.features.profile.ui.ProfileContract.Action.ShowThemeSelector
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.RequestLanguageSelector
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.RequestMessage
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.RequestProfileEditor
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.RequestThemeSelector
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.SaveProfile
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.SelectLanguage
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent.SelectTheme
import unowarder01.healthier.features.profile.ui.ProfileContract.Listener
import unowarder01.healthier.features.profile.ui.ProfileContract.Message.ComingSoon
import unowarder01.healthier.features.profile.ui.ProfileContract.Message.NotConfigured
import unowarder01.healthier.features.profile.ui.ProfileContract.State

class ProfileComponent(
    context: ComponentContext,
    viewModel: ProfileViewModel,
    private val navigator: ProfileNavigator
) : BaseFeatureComponent<
    State,
    Intent,
    Action,
    ProfileViewModel
>(
    context = context,
    viewModel = viewModel
), Listener {
    @Composable
    override fun subscribeState() = subscribe { action -> handle(action) }

    override fun onLocationChangeRequested() {
        navigator.changeLocation()
    }

    override fun onLanguageSelectorRequested() {
        intent(RequestLanguageSelector)
    }

    override fun onThemeSelectorRequested() {
        intent(RequestThemeSelector)
    }

    override fun onEditingStarted() {
        intent(RequestProfileEditor)
    }

    override fun onUnavailableActionSelected() {
        intent(RequestMessage(NotConfigured))
    }

    override fun onComingSoonActionSelected() {
        intent(RequestMessage(ComingSoon))
    }

    private fun handle(action: Action) {
        when (action) {
            is ShowProfileEditor -> navigator.showProfileEditor(
                profile = action.profile,
                language = action.language,
                onSave = { name, avatarReference ->
                    intent(SaveProfile(name, avatarReference))
                }
            )
            is ShowLanguageSelector -> navigator.showLanguageSelector(
                language = action.language,
                onSelect = { intent(SelectLanguage(it)) }
            )
            is ShowThemeSelector -> navigator.showThemeSelector(
                language = action.language,
                theme = action.theme,
                onSelect = { intent(SelectTheme(it)) }
            )
            is ShowMessage -> navigator.showMessage(
                language = action.language,
                message = action.message
            )
        }
    }
}
