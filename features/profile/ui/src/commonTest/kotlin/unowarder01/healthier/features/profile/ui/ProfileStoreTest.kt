package unowarder01.healthier.features.profile.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.features.profile.domain.Profile
import unowarder01.healthier.features.profile.domain.ProfileRepository
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppLanguageUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppThemeUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateProfileParams
import unowarder01.healthier.features.profile.domain.usecase.UpdateProfileUseCase

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ProfileStoreTest {
    @Test
    fun updatesStateAndRoutesOverlaysThroughActions() = runTest {
        val repository = FakeProfileRepository()
        var selectedLanguage = AppLanguage.English
        var selectedTheme = AppTheme.System
        val viewModel = ProfileViewModel(
            repository = repository,
            updateProfile = object : UpdateProfileUseCase {
                override suspend fun invoke(params: UpdateProfileParams) {
                    repository.update(params.name.trim(), params.avatarReference)
                }
            },
            updateLanguage = object : UpdateAppLanguageUseCase {
                override suspend fun invoke(params: AppLanguage) {
                    selectedLanguage = params
                }
            },
            updateTheme = object : UpdateAppThemeUseCase {
                override suspend fun invoke(params: AppTheme) {
                    selectedTheme = params
                }
            },
            language = AppLanguage.English,
            theme = AppTheme.System
        )
        val store = viewModel.store
        var latest = ProfileContract.State(
            repository.profile.value,
            AppLanguage.English,
            AppTheme.System
        )
        val actions = mutableListOf<ProfileContract.Action>()
        store.start(backgroundScope)
        with(store) {
            backgroundScope.subscribe { states.collect { latest = it } }
            backgroundScope.subscribe { this.actions.collect { actions += it } }
        }
        runCurrent()

        store.intent(ProfileContract.Intent.RequestProfileEditor)
        runCurrent()
        store.intent(ProfileContract.Intent.SaveProfile("  Ana  ", null))
        runCurrent()
        store.intent(ProfileContract.Intent.SelectLanguage(AppLanguage.Georgian))
        runCurrent()
        store.intent(ProfileContract.Intent.SelectTheme(AppTheme.Dark))
        runCurrent()
        store.intent(
            ProfileContract.Intent.RequestMessage(
                ProfileContract.Message.NotConfigured
            )
        )
        runCurrent()

        assertEquals("Ana", latest.profile.name)
        assertEquals(AppLanguage.Georgian, latest.language)
        assertEquals(AppTheme.Dark, latest.theme)
        assertEquals(AppLanguage.Georgian, selectedLanguage)
        assertEquals(AppTheme.Dark, selectedTheme)
        assertTrue(actions.any { it is ProfileContract.Action.ShowProfileEditor })
        assertTrue(actions.any { it is ProfileContract.Action.ShowMessage })
    }
}

private class FakeProfileRepository : ProfileRepository {
    override val profile = MutableStateFlow(Profile("Demo", null))

    override suspend fun update(
        name: String,
        avatarReference: String?
    ) {
        profile.value = Profile(name, avatarReference)
    }
}
