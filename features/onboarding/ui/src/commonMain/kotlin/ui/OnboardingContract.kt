package ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import ui.content.OnboardingData
import ui.content.getOnboardingItemsData

object OnboardingContract {
    sealed interface Intent: MVIIntent {
        data object OnPositiveButtonClicked: Intent
        data object OnNegativeButtonClicked: Intent
        data class HandleNotificationStatus(val granted: NotificationStatus): Intent
    }

    data class State(
        val items: List<OnboardingData> = getOnboardingItemsData(),
        val currentPage: Int = 0
    ): MVIState

    enum class NotificationStatus {
        Skipped,
        Granted,
        NotAllowed
    }

    sealed interface Action: MVIAction {
        data object NavigateToAuth: Action
        data object RequestNotificationsPermission: Action
    }

    interface Listener {
        fun onPositiveButtonClick()
        fun onNegativeButtonClick()
    }
}
