package unowarder01.healthier.features.city.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.common.extensions.runCatchingApp
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.city.ui.CityContract.Action
import unowarder01.healthier.features.city.ui.CityContract.Intent
import unowarder01.healthier.features.city.ui.CityContract.State
import unowarder01.healthier.features.city.ui.mapper.CitiesUiMapper
import unowarder01.healthier.features.city.usecase.GetCitiesUseCase

private typealias Ctx = PipelineContext<State, Intent, Action>

class CityViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val citiesUiMapper: CitiesUiMapper
): BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Ctx.init() {
        getCities()
    }

    private fun Ctx.getCities() = io {
        runCatchingApp {
            val citiesDomain = getCitiesUseCase()
            citiesUiMapper.map(citiesDomain)
        }.onSuccess { citiesUi ->
            updateState { copy(citiesUi = citiesUi) }
        }
    }
}
