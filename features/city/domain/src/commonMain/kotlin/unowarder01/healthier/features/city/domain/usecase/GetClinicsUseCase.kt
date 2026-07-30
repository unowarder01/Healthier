package unowarder01.healthier.features.city.domain.usecase

import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.domain.ClinicRepository

data class GetClinicsParams(
    val cityId: String,
    val forceRefresh: Boolean = false
)

interface GetClinicsUseCase : BaseUseCase<GetClinicsParams, AppResult<List<Clinic>>>

internal class GetClinicsUseCaseImpl(
    private val repository: ClinicRepository
) : GetClinicsUseCase {
    override suspend fun invoke(params: GetClinicsParams): AppResult<List<Clinic>> =
        repository.getClinics(
            cityId = params.cityId,
            forceRefresh = params.forceRefresh
        )
}
