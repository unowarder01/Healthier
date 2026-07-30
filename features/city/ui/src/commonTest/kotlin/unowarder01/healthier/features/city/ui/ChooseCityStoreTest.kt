package unowarder01.healthier.features.city.ui

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.features.city.domain.City
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.domain.usecase.SearchCitiesUseCase
import unowarder01.healthier.features.city.domain.usecase.SelectCityUseCase

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ChooseCityStoreTest {
    @Test
    fun loadSearchAndSuccessfulSelectionNavigate() = runTest {
        var saved: String? = null
        val store = ChooseCityViewModel(
            searchCities = FakeSearchCitiesUseCase(),
            selectCity = FakeSelectCityUseCase(AppResult.Success(listOf(clinic))) {
                saved = it
            }
        ).store
        var latest = ChooseCityContract.State()
        val actions = mutableListOf<ChooseCityContract.Action>()
        store.start(backgroundScope)
        with(store) {
            backgroundScope.subscribe { states.collect { latest = it } }
            backgroundScope.subscribe { this.actions.collect { actions += it } }
        }
        runCurrent()

        store.intent(ChooseCityContract.Intent.Load)
        runCurrent()
        store.intent(ChooseCityContract.Intent.QueryChanged("  bat  "))
        runCurrent()
        store.intent(ChooseCityContract.Intent.SelectCity("batumi"))
        runCurrent()

        assertEquals("  bat  ", latest.query)
        assertEquals(listOf("batumi"), latest.cities.map(City::id))
        assertNull(latest.loadingCityId)
        assertEquals("batumi", saved)
        assertTrue(actions.single() is ChooseCityContract.Action.NavigateHome)
    }

    @Test
    fun selectionFailureMarksOnlyRequestedCity() = runTest {
        val store = ChooseCityViewModel(
            searchCities = FakeSearchCitiesUseCase(),
            selectCity = FakeSelectCityUseCase(AppResult.Failure(AppError.Offline))
        ).store
        var latest = ChooseCityContract.State()
        store.start(backgroundScope)
        with(store) { backgroundScope.subscribe { states.collect { latest = it } } }
        runCurrent()

        store.intent(ChooseCityContract.Intent.SelectCity("tbilisi"))
        runCurrent()

        assertNull(latest.loadingCityId)
        assertEquals("tbilisi", latest.errorCityId)
    }
}

private val clinic = Clinic("1", "batumi", "Clinic", "Care", "Address", 1.0, 2.0, null)
private val cities = listOf(
    City("tbilisi", mapOf(AppLanguage.English to "Tbilisi"), emptySet(), 100),
    City("batumi", mapOf(AppLanguage.English to "Batumi"), emptySet(), 50)
)

private class FakeSearchCitiesUseCase : SearchCitiesUseCase {
    override suspend fun invoke(params: String): List<City> =
        if (params.trim().isEmpty()) {
            cities
        } else {
            cities.filter {
                it.name(AppLanguage.English).contains(params.trim(), true)
            }
        }
}

private class FakeSelectCityUseCase(
    private val result: AppResult<List<Clinic>>,
    private val onSuccess: (String) -> Unit = {}
) : SelectCityUseCase {
    override suspend fun invoke(params: String): AppResult<List<Clinic>> =
        result.also {
            if (it is AppResult.Success) onSuccess(params)
        }
}
