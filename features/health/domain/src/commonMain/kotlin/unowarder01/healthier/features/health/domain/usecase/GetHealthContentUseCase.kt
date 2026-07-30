package unowarder01.healthier.features.health.domain.usecase

import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.health.domain.HealthContent
import unowarder01.healthier.features.health.domain.HealthRepository

interface GetHealthContentUseCase : BaseUseCase<List<Clinic>, HealthContent>

internal class GetHealthContentUseCaseImpl(
    private val repository: HealthRepository
) : GetHealthContentUseCase {
    override suspend fun invoke(params: List<Clinic>) = HealthContent(
        clinics = params,
        doctors = repository.doctors(),
        stories = repository.stories()
    )
}
