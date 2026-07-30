package unowarder01.healthier.features.city.domain.usecase

import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.domain.ClinicRepository
import unowarder01.healthier.features.city.domain.SelectedCityRepository

interface SelectCityUseCase : BaseUseCase<String, AppResult<List<Clinic>>>

internal class SelectCityUseCaseImpl(
    private val clinics: ClinicRepository,
    private val selectedCity: SelectedCityRepository
) : SelectCityUseCase {
    override suspend fun invoke(params: String): AppResult<List<Clinic>> =
        clinics.getClinics(params).also { result ->
            if (result is AppResult.Success) selectedCity.save(params)
        }
}
