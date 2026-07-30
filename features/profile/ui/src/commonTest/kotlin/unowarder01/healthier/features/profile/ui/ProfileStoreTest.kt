package unowarder01.healthier.features.profile.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.platform.PhotoPicker
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.features.profile.domain.Profile
import unowarder01.healthier.features.profile.domain.ProfileRepository
import unowarder01.healthier.features.profile.domain.UpdateAppLanguageUseCase
import unowarder01.healthier.features.profile.domain.UpdateAppThemeUseCase
import unowarder01.healthier.features.profile.domain.UpdateProfileUseCase

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ProfileStoreTest {
    @Test
    fun editSaveSelectorsAndPickerErrorAreStateDriven() = runTest {
        val repository = FakeProfileRepository()
        val settings = FakeProfileSettings()
        val factory = ProfileStoreFactory(
            repository,
            UpdateProfileUseCase(repository),
            UpdateAppLanguageUseCase(settings),
            UpdateAppThemeUseCase(settings),
            object : PhotoPicker {
                override suspend fun pickAvatar(): AppResult<String> =
                    AppResult.Failure(AppError.NotConfigured)
            },
        )
        val store = factory.create(AppLanguage.English, AppTheme.System)
        var latest = ProfileContract.State(repository.profile.value, AppLanguage.English, AppTheme.System)
        store.start(backgroundScope)
        with(store) { backgroundScope.subscribe { states.collect { latest = it } } }
        runCurrent()

        store.intent(ProfileContract.Intent.StartEdit)
        runCurrent()
        store.intent(ProfileContract.Intent.NameChanged("  Ana  "))
        runCurrent()
        store.intent(ProfileContract.Intent.SaveProfile)
        runCurrent()
        assertFalse(latest.editing)
        assertEquals("Ana", latest.profile.name)

        store.intent(ProfileContract.Intent.ShowLanguageSelector)
        runCurrent()
        store.intent(ProfileContract.Intent.SelectLanguage(AppLanguage.Georgian))
        runCurrent()
        store.intent(ProfileContract.Intent.ShowThemeSelector)
        runCurrent()
        store.intent(ProfileContract.Intent.SelectTheme(AppTheme.Dark))
        runCurrent()
        store.intent(ProfileContract.Intent.PickAvatar)
        runCurrent()

        assertEquals(AppLanguage.Georgian, latest.language)
        assertEquals(AppTheme.Dark, latest.theme)
        assertFalse(latest.showLanguageSelector)
        assertFalse(latest.showThemeSelector)
        assertEquals(ProfileContract.Message.NotConfigured, latest.message)
        assertTrue(settings.language.value == AppLanguage.Georgian && settings.theme.value == AppTheme.Dark)
    }
}

private class FakeProfileRepository : ProfileRepository {
    override val profile = MutableStateFlow(Profile("Demo", null))
    override suspend fun update(name: String, avatarReference: String?) {
        profile.value = Profile(name, avatarReference)
    }
}

private class FakeProfileSettings : SettingsRepository {
    override val language = MutableStateFlow(AppLanguage.English)
    override val theme = MutableStateFlow(AppTheme.System)
    override val selectedCityId = MutableStateFlow<String?>(null)
    override fun setLanguage(value: AppLanguage) { language.value = value }
    override fun setTheme(value: AppTheme) { theme.value = value }
    override fun setSelectedCityId(value: String) { selectedCityId.value = value }
}
