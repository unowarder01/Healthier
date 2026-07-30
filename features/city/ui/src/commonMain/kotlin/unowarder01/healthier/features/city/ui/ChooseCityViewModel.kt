package unowarder01.healthier.features.city.ui

import org.koin.dsl.module
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.StoreViewModel
import unowarder01.healthier.features.city.domain.SearchCitiesUseCase
import unowarder01.healthier.features.city.domain.SelectCityUseCase

class ChooseCityStoreFactory(
    private val searchCities: SearchCitiesUseCase,
    private val selectCity: SelectCityUseCase,
) {
    fun create() =
        healthierStore<ChooseCityContract.State, ChooseCityContract.Intent, ChooseCityContract.Action>(
            name = "city.choose-city",
            initial = ChooseCityContract.State(),
        ) { intent ->
            when (intent) {
                ChooseCityContract.Intent.Load -> {
                    val cities = searchCities("")
                    updateState { copy(cities = cities) }
                }
                is ChooseCityContract.Intent.QueryChanged -> {
                    val cities = searchCities(intent.value)
                    updateState {
                        copy(query = intent.value, cities = cities, errorCityId = null)
                    }
                }
                is ChooseCityContract.Intent.SelectCity -> {
                    if (currentState().loadingCityId != null) return@healthierStore
                    updateState { copy(loadingCityId = intent.cityId, errorCityId = null) }
                    when (val result = selectCity(intent.cityId)) {
                        is AppResult.Success -> {
                            updateState { copy(loadingCityId = null) }
                            action(ChooseCityContract.Action.NavigateHome(result.value))
                        }
                        is AppResult.Failure -> updateState {
                            copy(loadingCityId = null, errorCityId = intent.cityId)
                        }
                    }
                }
            }
        }
}

class ChooseCityViewModel(factory: ChooseCityStoreFactory) :
    StoreViewModel<ChooseCityContract.State, ChooseCityContract.Intent, ChooseCityContract.Action>(
        factory.create()
    )

val cityUiModule = module {
    factory { ChooseCityStoreFactory(get(), get()) }
}
