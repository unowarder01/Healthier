package unowarder01.healthier.features.profile.ui

import org.koin.dsl.module
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.platform.PhotoPicker
import unowarder01.healthier.core.presentation.StoreViewModel
import unowarder01.healthier.features.profile.domain.ProfileRepository
import unowarder01.healthier.features.profile.domain.UpdateAppLanguageUseCase
import unowarder01.healthier.features.profile.domain.UpdateAppThemeUseCase
import unowarder01.healthier.features.profile.domain.UpdateProfileUseCase

class ProfileStoreFactory(
    private val repository: ProfileRepository,
    private val updateProfile: UpdateProfileUseCase,
    private val updateLanguage: UpdateAppLanguageUseCase,
    private val updateTheme: UpdateAppThemeUseCase,
    private val photoPicker: PhotoPicker,
) {
    fun create(language: AppLanguage, theme: AppTheme) =
        healthierStore<ProfileContract.State, ProfileContract.Intent, ProfileContract.Action>(
            name = "profile.overview",
            initial = ProfileContract.State(repository.profile.value, language, theme),
        ) { intent ->
            when (intent) {
                ProfileContract.Intent.StartEdit -> updateState {
                    copy(
                        editing = true,
                        draftName = profile.name,
                        draftAvatar = profile.avatarReference,
                        message = null,
                    )
                }
                ProfileContract.Intent.DismissEdit -> updateState { copy(editing = false) }
                is ProfileContract.Intent.NameChanged -> updateState { copy(draftName = intent.value) }
                ProfileContract.Intent.PickAvatar -> {
                    when (val result = photoPicker.pickAvatar()) {
                        is AppResult.Success -> updateState { copy(draftAvatar = result.value) }
                        is AppResult.Failure -> updateState { copy(message = ProfileContract.Message.NotConfigured) }
                    }
                }
                ProfileContract.Intent.SaveProfile -> {
                    val snapshot = currentState()
                    updateProfile(snapshot.draftName, snapshot.draftAvatar)
                    updateState {
                        copy(
                            profile = repository.profile.value,
                            editing = false,
                            message = null,
                        )
                    }
                }
                ProfileContract.Intent.ShowLanguageSelector ->
                    updateState { copy(showLanguageSelector = true, message = null) }
                ProfileContract.Intent.ShowThemeSelector ->
                    updateState { copy(showThemeSelector = true, message = null) }
                is ProfileContract.Intent.SelectLanguage -> {
                    updateLanguage(intent.language)
                    updateState { copy(language = intent.language, showLanguageSelector = false) }
                }
                is ProfileContract.Intent.SelectTheme -> {
                    updateTheme(intent.theme)
                    updateState { copy(theme = intent.theme, showThemeSelector = false) }
                }
                is ProfileContract.Intent.ShowMessage ->
                    updateState { copy(message = intent.message) }
                ProfileContract.Intent.DismissOverlay -> updateState {
                    copy(
                        showLanguageSelector = false,
                        showThemeSelector = false,
                        message = null,
                    )
                }
            }
        }
}

class ProfileViewModel(
    factory: ProfileStoreFactory,
    language: AppLanguage,
    theme: AppTheme,
) : StoreViewModel<ProfileContract.State, ProfileContract.Intent, ProfileContract.Action>(
    factory.create(language, theme)
)

val profileUiModule = module {
    factory { ProfileStoreFactory(get(), get(), get(), get(), get()) }
}
