package unowarder01.healthier.features.splash.ui

import kotlinx.coroutines.delay
import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.splash.domain.usecase.SelectLanguageUseCase
import unowarder01.healthier.features.splash.ui.SplashContract.Action
import unowarder01.healthier.features.splash.ui.SplashContract.Action.NavigateToAuth
import unowarder01.healthier.features.splash.ui.SplashContract.Intent
import unowarder01.healthier.features.splash.ui.SplashContract.Intent.OnLanguageClicked
import unowarder01.healthier.features.splash.ui.SplashContract.State
import kotlin.time.Duration.Companion.milliseconds

private typealias Ctx = PipelineContext<State, Intent, Action>

class SplashViewModel(
    private val selectLanguageUseCase: SelectLanguageUseCase
) : BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Ctx.init() {
        delay(2500.milliseconds) // emulate API query
        updateState { copy(languages = AppLanguage.entries) }
    }

    override suspend fun Ctx.handleIntent(intent: Intent) {
        when (intent) {
            is OnLanguageClicked -> {
                selectLanguageUseCase(intent.language)
                updateState { copy(selectedLanguage = intent.language) }
                delay(300.milliseconds) // RadioButton select animation duration
                action(NavigateToAuth)
            }
        }
    }
}
