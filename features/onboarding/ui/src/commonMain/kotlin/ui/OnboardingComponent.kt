package ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import ui.OnboardingContract.Action
import ui.OnboardingContract.Action.NavigateToAuth
import ui.OnboardingContract.Action.RequestNotificationsPermission
import ui.OnboardingContract.Intent
import ui.OnboardingContract.Intent.HandleNotificationStatus
import ui.OnboardingContract.Intent.OnNegativeButtonClicked
import ui.OnboardingContract.Intent.OnPositiveButtonClicked
import ui.OnboardingContract.Listener
import ui.OnboardingContract.NotificationStatus.Granted
import ui.OnboardingContract.NotificationStatus.NotAllowed
import ui.OnboardingContract.State
import unowarder01.healthier.core.designsystem.typealiases.ComposeState
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.core.presentation.permissions.LocalNotificationPermissionRequester

class OnboardingComponent(
    context: ComponentContext,
    viewModel: OnboardingViewModel,
    private val navigator: OnboardingNavigator
): Listener, BaseComponent<State, Intent, Action, OnboardingViewModel>(
    context = context,
    viewModel = viewModel
) {
    /**
     * STATE
     */
    @Composable
    override fun subscribeState(): ComposeState<State> {
        val requester = LocalNotificationPermissionRequester.current
        return subscribe { action ->
            when (action) {
                is NavigateToAuth -> {
                    navigator.toAuth()
                }
                is RequestNotificationsPermission -> requester.request { granted ->
                    val status = if (granted) Granted else NotAllowed
                    intent(HandleNotificationStatus(status))
                }
            }
        }
    }

    /**
     * LISTENER
     */
    override fun onPositiveButtonClick() {
        intent(OnPositiveButtonClicked)
    }

    override fun onNegativeButtonClick() {
        intent(OnNegativeButtonClicked)
    }
}
