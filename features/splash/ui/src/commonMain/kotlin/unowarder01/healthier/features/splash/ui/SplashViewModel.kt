package unowarder01.healthier.features.splash.ui

import kotlinx.coroutines.delay
import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.splash.domain.usecase.SelectLanguageUseCase
import unowarder01.healthier.features.splash.ui.SplashContract.Action
import unowarder01.healthier.features.splash.ui.SplashContract.Action.NavigateToAuth
import unowarder01.healthier.features.splash.ui.SplashContract.Intent
import unowarder01.healthier.features.splash.ui.SplashContract.Intent.SelectLanguage
import unowarder01.healthier.features.splash.ui.SplashContract.State

private typealias Context = PipelineContext<State, Intent, Action>

class SplashViewModel(
    private val selectLanguage: SelectLanguageUseCase
) : BaseViewModel<State, Intent, Action>(
    initialState = State(),
    storeKey = "splash.language"
) {
    override suspend fun Context.handleIntent(intent: Intent) {
        when (intent) {
            is SelectLanguage -> {
                if (currentState().exiting) return
                selectLanguage(intent.language)
                updateState { copy(selected = intent.language, exiting = true) }
                delay(220)
                action(NavigateToAuth)
            }
        }
    }
}
