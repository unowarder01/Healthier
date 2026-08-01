package ui

import pro.respawn.flowmvi.api.PipelineContext
import ui.OnboardingContract.Action
import ui.OnboardingContract.Action.NavigateToAuth
import ui.OnboardingContract.Action.RequestNotificationsPermission
import ui.OnboardingContract.Intent
import ui.OnboardingContract.Intent.OnNegativeButtonClicked
import ui.OnboardingContract.Intent.OnPositiveButtonClicked
import ui.OnboardingContract.State
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel

private typealias Ctx = PipelineContext<State, Intent, Action>

class OnboardingViewModel: BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Ctx.handleIntent(intent: Intent) {
        when (intent) {
            is OnPositiveButtonClicked -> handleOnPositiveButtonClick()
            is OnNegativeButtonClicked -> handleOnNegativeButtonClick()
        }
    }

    private suspend fun Ctx.handleOnPositiveButtonClick() = withState {
        when {
            currentPage < items.lastIndex -> updateState { copy(currentPage = currentPage + 1) }
            else -> action(RequestNotificationsPermission)
        }
    }

    private suspend fun Ctx.handleOnNegativeButtonClick() {
        action(NavigateToAuth)
    }
}
