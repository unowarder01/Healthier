package unowarder01.healthier.features.city.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.database.ClinicCache
import unowarder01.healthier.core.database.ClinicCacheRecord
import unowarder01.healthier.core.network.NetworkEnvironment
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.features.city.domain.City
import unowarder01.healthier.features.city.domain.CityRepository
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.domain.ClinicRepository
import unowarder01.healthier.features.city.domain.SelectedCityRepository

class CityRepositoryImpl : CityRepository {
    private val cities = listOf(
        city("tbilisi", "თბილისი", "Tbilisi", "Тбилиси", 1_108_717, "tiflis", "тбилиси"),
        city("batumi", "ბათუმი", "Batumi", "Батуми", 152_839, "батум"),
        city("kutaisi", "ქუთაისი", "Kutaisi", "Кутаиси", 147_635, "кутаиси"),
        city("rustavi", "რუსთავი", "Rustavi", "Рустави", 125_103, "рустави"),
        city("gori", "გორი", "Gori", "Гори", 48_143, "гори"),
        city("zugdidi", "ზუგდიდი", "Zugdidi", "Зугдиди", 42_998, "зугдиди"),
        city("poti", "ფოთი", "Poti", "Поти", 41_465, "поти"),
        city("khashuri", "ხაშური", "Khashuri", "Хашури", 26_135, "хашури"),
        city("samtredia", "სამტრედია", "Samtredia", "Самтредиа", 25_318, "самтредиа"),
        city("senaki", "სენაკი", "Senaki", "Сенаки", 21_596, "сенаки"),
        city("zestafoni", "ზესტაფონი", "Zestafoni", "Зестафони", 20_814, "зестафони"),
        city("marneuli", "მარნეული", "Marneuli", "Марнеули", 20_211, "марнеули")
    ).sortedByDescending(City::population)

    override fun observeCities(): Flow<List<City>> = flowOf(cities)

    override suspend fun searchCities(query: String): List<City> {
        val normalized = query.trim()
        if (normalized.isEmpty()) return cities
        return cities.filter { city ->
            city.names.values.any { it.contains(normalized, ignoreCase = true) } ||
                city.aliases.any { it.contains(normalized, ignoreCase = true) }
        }
    }
}

private fun city(
    id: String,
    ka: String,
    en: String,
    ru: String,
    population: Int,
    vararg aliases: String
) = City(
    id = id,
    names = mapOf(
        AppLanguage.Georgian to ka,
        AppLanguage.English to en,
        AppLanguage.Russian to ru
    ),
    aliases = aliases.toSet() + setOf(ka, en, ru),
    population = population
)

@Serializable
data class ClinicDto(
    val id: String,
    val cityId: String,
    val name: String,
    val specialization: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String? = null
)

@Serializable
data class ClinicsResponseDto(
    val version: Int,
    val cityId: String,
    val clinics: List<ClinicDto>
)

interface ClinicRemoteSource {
    suspend fun clinics(cityId: String): AppResult<List<ClinicDto>>
}

class KtorClinicRemoteSource(
    private val client: HttpClient,
    private val environment: NetworkEnvironment
) : ClinicRemoteSource {
    override suspend fun clinics(cityId: String): AppResult<List<ClinicDto>> {
        if (environment.baseUrl.isBlank()) {
            return AppResult.Failure(AppError.NotConfigured)
        }
        return try {
        val response = client.get("${environment.baseUrl.trimEnd('/')}/v1/cities/$cityId/clinics")
        if (!response.status.isSuccess()) {
            AppResult.Failure(
                if (response.status.value == 404) AppError.NotFound else AppError.Http(response.status.value)
            )
        } else {
            AppResult.Success(response.body<ClinicsResponseDto>().clinics)
        }
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (_: Throwable) {
        AppResult.Failure(AppError.Offline)
        }
    }
}

class DemoClinicRemoteSource : ClinicRemoteSource {
    override suspend fun clinics(cityId: String): AppResult<List<ClinicDto>> {
        delay(450)
        return AppResult.Success(
            listOf(
                ClinicDto(
                    id = "$cityId-central",
                    cityId = cityId,
                    name = "Healthier Central",
                    specialization = "Multidisciplinary clinic",
                    address = "Demo address, $cityId",
                    latitude = if (cityId == "batumi") 41.6461 else 41.7151,
                    longitude = if (cityId == "batumi") 41.6405 else 44.8271
                ),
                ClinicDto(
                    id = "$cityId-family",
                    cityId = cityId,
                    name = "Family Care",
                    specialization = "Family medicine",
                    address = "Demo avenue, $cityId",
                    latitude = if (cityId == "batumi") 41.6500 else 41.7220,
                    longitude = if (cityId == "batumi") 41.6420 else 44.7900
                ),
                ClinicDto(
                    id = "$cityId-diagnostics",
                    cityId = cityId,
                    name = "Diagnostics Hub",
                    specialization = "Diagnostics",
                    address = "Demo square, $cityId",
                    latitude = if (cityId == "batumi") 41.6380 else 41.7040,
                    longitude = if (cityId == "batumi") 41.6210 else 44.8030
                )
            )
        )
    }
}

class ClinicRepositoryImpl(
    private val remote: ClinicRemoteSource,
    private val cache: ClinicCache
) : ClinicRepository {
    override suspend fun getClinics(cityId: String, forceRefresh: Boolean): AppResult<List<Clinic>> {
        if (!forceRefresh) {
            val cached = cache.clinics(cityId)
            if (cached.isNotEmpty()) return AppResult.Success(cached.map(ClinicCacheRecord::toDomain))
        }
        return when (val response = remote.clinics(cityId)) {
            is AppResult.Failure -> response
            is AppResult.Success -> {
                val records = response.value.map(ClinicDto::toRecord)
                cache.replace(cityId, records)
                AppResult.Success(records.map(ClinicCacheRecord::toDomain))
            }
        }
    }
}

private fun ClinicDto.toRecord() = ClinicCacheRecord(
    id, cityId, name, specialization, address, latitude, longitude, imageUrl
)

private fun ClinicCacheRecord.toDomain() = Clinic(
    id, cityId, name, specialization, address, latitude, longitude, imageUrl
)

class SelectedCityRepositoryImpl(
    private val settings: SettingsRepository
) : SelectedCityRepository {
    override fun save(cityId: String) = settings.setSelectedCityId(cityId)
}
