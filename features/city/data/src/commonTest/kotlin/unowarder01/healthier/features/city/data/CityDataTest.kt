package unowarder01.healthier.features.city.data

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.database.InMemoryClinicCache

class CityDataTest {
    @Test
    fun searchTrimsAliasesAndKeepsPopulationOrder() = runTest {
        val repository = CityRepositoryImpl()

        val all = repository.searchCities("   ")
        assertEquals("tbilisi", all.first().id)
        assertTrue(all.zipWithNext().all { (left, right) -> left.population >= right.population })
        assertEquals("tbilisi", repository.searchCities("  TIFLIS ").single().id)
        assertEquals("batumi", repository.searchCities("БАТУМ").single().id)
    }

    @Test
    fun clinicRepositoryMapsRemoteAndThenReadsCache() = runTest {
        var calls = 0
        val remote = object : ClinicRemoteSource {
            override suspend fun clinics(cityId: String): AppResult<List<ClinicDto>> {
                calls++
                return AppResult.Success(
                    listOf(ClinicDto("1", cityId, "Clinic", "Cardiology", "Address", 1.0, 2.0))
                )
            }
        }
        val repository = ClinicRepositoryImpl(remote, InMemoryClinicCache())

        val first = assertIs<AppResult.Success<*>>(repository.getClinics("tbilisi")).value
        val second = assertIs<AppResult.Success<*>>(repository.getClinics("tbilisi")).value

        assertEquals(1, calls)
        assertEquals(first, second)
    }

    @Test
    fun clinicRepositoryKeepsTypedRemoteError() = runTest {
        val remote = object : ClinicRemoteSource {
            override suspend fun clinics(cityId: String): AppResult<List<ClinicDto>> =
                AppResult.Failure(AppError.Offline)
        }

        val result = ClinicRepositoryImpl(remote, InMemoryClinicCache()).getClinics("tbilisi")
        assertEquals(AppError.Offline, assertIs<AppResult.Failure>(result).error)
    }
}
