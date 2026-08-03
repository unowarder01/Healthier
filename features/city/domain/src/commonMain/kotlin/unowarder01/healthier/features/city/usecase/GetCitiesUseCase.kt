package unowarder01.healthier.features.city.usecase

import unowarder01.healthier.core.common.usecase.UseCaseResult
import unowarder01.healthier.features.city.model.CityDomain
import unowarder01.healthier.features.city.repository.CityRepository

interface GetCitiesUseCase: UseCaseResult<List<CityDomain>>

internal class GetCitiesUseCaseImpl(
    private val repository: CityRepository
): GetCitiesUseCase {
    override suspend fun invoke() = repository.getCities()
}