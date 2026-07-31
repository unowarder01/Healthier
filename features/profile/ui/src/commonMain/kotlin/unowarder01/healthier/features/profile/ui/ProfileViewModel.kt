package unowarder01.healthier.features.profile.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.profile.domain.ProfileRepository
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppLanguageUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppThemeUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateProfileParams
import unowarder01.healthier.features.profile.domain.usecase.UpdateProfileUseCase
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
import unowarder01.healthier.features.profile.ui.ProfileContract.State

private typealias Context = PipelineContext<State, Intent, Action>

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val updateProfile: UpdateProfileUseCase,
    private val updateLanguage: UpdateAppLanguageUseCase,
    private val updateTheme: UpdateAppThemeUseCase,
    language: AppLanguage,
    theme: AppTheme
) : BaseViewModel<State, Intent, Action>(
    initialState = State(
        profile = repository.profile.value,
        language = language,
        theme = theme
    ),
) {
    override suspend fun Context.handleIntent(intent: Intent) {
        when (intent) {
            RequestProfileEditor -> withState {
                action(ShowProfileEditor(profile, language))
            }
            RequestLanguageSelector -> withState {
                action(ShowLanguageSelector(language))
            }
            RequestThemeSelector -> withState {
                action(ShowThemeSelector(language, theme))
            }
            is SaveProfile -> {
                updateProfile(
                    UpdateProfileParams(
                        name = intent.name,
                        avatarReference = intent.avatarReference
                    )
                )
                updateState { copy(profile = repository.profile.value) }
            }
            is SelectLanguage -> {
                updateLanguage(intent.language)
                updateState { copy(language = intent.language) }
            }
            is SelectTheme -> {
                updateTheme(intent.theme)
                updateState { copy(theme = intent.theme) }
            }
            is RequestMessage -> withState {
                action(ShowMessage(language, intent.message))
            }
        }
    }
}
