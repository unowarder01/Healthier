package unowarder01.healthier.features.city.ui

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import unowarder01.healthier.features.city.ui.data.CitiesUi

object CityContract {
    sealed interface Intent : MVIIntent

    data class State(
        val citiesUi: List<CitiesUi> = listOf()
    ): MVIState

    sealed interface Action : MVIAction

    interface Listener {
        fun onCityClick()
    }
}
