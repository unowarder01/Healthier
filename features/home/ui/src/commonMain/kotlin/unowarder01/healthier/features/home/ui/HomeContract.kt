package unowarder01.healthier.features.home.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.features.home.domain.HomeTab

object HomeContract {
    data class State(val selectedTab: HomeTab = HomeTab.Health) : MVIState

    sealed interface Intent : MVIIntent {
        data class SelectTab(val tab: HomeTab) : Intent
    }

    sealed interface Action : MVIAction
}
