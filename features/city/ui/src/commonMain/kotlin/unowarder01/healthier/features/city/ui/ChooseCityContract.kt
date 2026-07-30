package unowarder01.healthier.features.city.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.features.city.domain.City
import unowarder01.healthier.features.city.domain.Clinic

object ChooseCityContract {
    data class State(
        val query: String = "",
        val cities: List<City> = emptyList(),
        val loadingCityId: String? = null,
        val errorCityId: String? = null
    ) : MVIState

    sealed interface Intent : MVIIntent {
        data object Load : Intent
        data class QueryChanged(val value: String) : Intent
        data class SelectCity(val cityId: String) : Intent
    }

    sealed interface Action : MVIAction {
        data class NavigateHome(val clinics: List<Clinic>) : Action
    }

    interface Listener {
        fun onScreenShown()
        fun onQueryChanged(query: String)
        fun onCitySelected(cityId: String)
    }
}
