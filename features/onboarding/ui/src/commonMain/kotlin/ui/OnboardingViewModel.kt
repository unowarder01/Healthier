package ui

import pro.respawn.flowmvi.api.PipelineContext
import ui.OnboardingContract.Action
import ui.OnboardingContract.Action.NavigateToAuth
import ui.OnboardingContract.Action.RequestNotificationsPermission
import ui.OnboardingContract.Intent
import ui.OnboardingContract.Intent.HandleNotificationStatus
import ui.OnboardingContract.Intent.OnNegativeButtonClicked
import ui.OnboardingContract.Intent.OnPositiveButtonClicked
import ui.OnboardingContract.NotificationStatus
import ui.OnboardingContract.NotificationStatus.Skipped
import ui.OnboardingContract.State
import ui.content.OnboardingData.ReminderAndResults
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel

private typealias Ctx = PipelineContext<State, Intent, Action>

class OnboardingViewModel: BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Ctx.handleIntent(intent: Intent) {
        when (intent) {
            is OnPositiveButtonClicked -> handleOnPositiveButtonClick()
            is OnNegativeButtonClicked -> handleOnNegativeButtonClick()
            is HandleNotificationStatus -> handleNotificationStatus(intent.granted)
        }
    }

    private suspend fun Ctx.handleOnPositiveButtonClick() = withState {
        when (items[currentPage]) {
            is ReminderAndResults -> action(RequestNotificationsPermission)
            else -> updateState { copy(currentPage = currentPage + 1) }
        }
    }

    private suspend fun Ctx.handleOnNegativeButtonClick() = withState {
        when (items[currentPage]) {
            is ReminderAndResults -> handleNotificationStatus(Skipped)
            else -> action(NavigateToAuth)
        }
    }

    private suspend fun Ctx.handleNotificationStatus(status: NotificationStatus) {
        // TODO: Send status to analytics
        action(NavigateToAuth)
    }
}
