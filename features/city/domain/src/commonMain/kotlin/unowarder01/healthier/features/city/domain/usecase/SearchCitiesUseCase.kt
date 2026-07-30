package unowarder01.healthier.features.city.domain.usecase

import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.features.city.domain.City
import unowarder01.healthier.features.city.domain.CityRepository

interface SearchCitiesUseCase : BaseUseCase<String, List<City>>

internal class SearchCitiesUseCaseImpl(
    private val repository: CityRepository
) : SearchCitiesUseCase {
    override suspend fun invoke(params: String): List<City> =
        repository.searchCities(params.trim()).sortedByDescending(City::population)
}
