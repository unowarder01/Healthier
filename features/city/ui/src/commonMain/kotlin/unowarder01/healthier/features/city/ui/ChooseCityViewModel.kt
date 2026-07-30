package unowarder01.healthier.features.city.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.city.domain.usecase.SearchCitiesUseCase
import unowarder01.healthier.features.city.domain.usecase.SelectCityUseCase
import unowarder01.healthier.features.city.ui.ChooseCityContract.Action
import unowarder01.healthier.features.city.ui.ChooseCityContract.Action.NavigateHome
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent.Load
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent.QueryChanged
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent.SelectCity
import unowarder01.healthier.features.city.ui.ChooseCityContract.State

private typealias Context = PipelineContext<State, Intent, Action>

class ChooseCityViewModel(
    private val searchCities: SearchCitiesUseCase,
    private val selectCity: SelectCityUseCase
) : BaseViewModel<State, Intent, Action>(
    initialState = State(),
    storeKey = "city.choose-city"
) {
    override suspend fun Context.handleIntent(intent: Intent) {
        when (intent) {
            Load -> {
                val cities = searchCities("")
                updateState { copy(cities = cities) }
            }
            is QueryChanged -> {
                val cities = searchCities(intent.value)
                updateState {
                    copy(query = intent.value, cities = cities, errorCityId = null)
                }
            }
            is SelectCity -> {
                if (currentState().loadingCityId != null) return
                updateState { copy(loadingCityId = intent.cityId, errorCityId = null) }
                when (val result = selectCity(intent.cityId)) {
                    is AppResult.Success -> {
                        updateState { copy(loadingCityId = null) }
                        action(NavigateHome(result.value))
                    }
                    is AppResult.Failure -> updateState {
                        copy(loadingCityId = null, errorCityId = intent.cityId)
                    }
                }
            }
        }
    }
}
