package unowarder01.healthier.features.city.domain

import unowarder01.healthier.core.common.AppResult

interface ClinicRepository {
    suspend fun getClinics(
        cityId: String,
        forceRefresh: Boolean = false
    ): AppResult<List<Clinic>>
}
