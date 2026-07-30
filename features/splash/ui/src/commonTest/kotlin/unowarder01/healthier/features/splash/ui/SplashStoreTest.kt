package unowarder01.healthier.features.splash.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.features.splash.domain.usecase.SelectLanguageUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class SplashStoreTest {
    @Test
    fun revealAndSelectionUpdateStateAndNavigate() = runTest {
        val settings = FakeSettingsRepository()
        val useCase = object : SelectLanguageUseCase {
            override suspend fun invoke(params: AppLanguage) {
                settings.setLanguage(params)
            }
        }
        val store = SplashViewModel(useCase).store
        var latest = SplashContract.State()
        val actions = mutableListOf<SplashContract.Action>()
        store.start(backgroundScope)
        with(store) {
            backgroundScope.subscribe { states.collect { latest = it } }
            backgroundScope.subscribe {
                this.actions.collect { action -> actions += action }
            }
        }
        runCurrent()

        store.intent(SplashContract.Intent.SelectLanguage(AppLanguage.Russian))
        runCurrent()
        advanceTimeBy(221)
        runCurrent()
        assertEquals(AppLanguage.Russian, latest.selected)
        assertTrue(latest.exiting)
        assertEquals(AppLanguage.Russian, settings.language.value)
        assertTrue(SplashContract.Action.NavigateToAuth in actions)
    }
}

private class FakeSettingsRepository : SettingsRepository {
    override val language = MutableStateFlow(AppLanguage.English)
    override val theme = MutableStateFlow(AppTheme.System)
    override val selectedCityId = MutableStateFlow<String?>(null)
    override fun setLanguage(value: AppLanguage) { language.value = value }
    override fun setTheme(value: AppTheme) { theme.value = value }
    override fun setSelectedCityId(value: String) { selectedCityId.value = value }
}
