package unowarder01.healthier.features.health.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.features.health.domain.HealthContent

object HealthContract {
    data class State(
        val query: String = "",
        val content: HealthContent,
        val filtered: HealthContent = content
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data object Load : Intent
        data class QueryChanged(val value: String) : Intent
    }

    sealed interface Action : MVIAction

    interface Listener {
        fun onScreenShown()
        fun onQueryChanged(query: String)
        fun onLocationChangeRequested()
        fun onLanguageChangeRequested()
    }
}
