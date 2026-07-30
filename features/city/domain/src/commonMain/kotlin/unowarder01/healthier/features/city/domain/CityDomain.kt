package unowarder01.healthier.features.city.domain

import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppResult

data class City(
    val id: String,
    val names: Map<AppLanguage, String>,
    val aliases: Set<String>,
    val population: Int,
) {
    fun name(language: AppLanguage): String =
        names[language] ?: names.getValue(AppLanguage.English)
}

data class Clinic(
    val id: String,
    val cityId: String,
    val name: String,
    val specialization: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String?,
)

interface CityRepository {
    fun observeCities(): Flow<List<City>>
    suspend fun searchCities(query: String): List<City>
}

interface ClinicRepository {
    suspend fun getClinics(cityId: String, forceRefresh: Boolean = false): AppResult<List<Clinic>>
}

interface SelectedCityRepository {
    fun save(cityId: String)
}

class SearchCitiesUseCase(
    private val repository: CityRepository,
) {
    suspend operator fun invoke(query: String): List<City> =
        repository.searchCities(query.trim()).sortedByDescending(City::population)
}

class GetClinicsUseCase(
    private val repository: ClinicRepository,
) {
    suspend operator fun invoke(cityId: String, forceRefresh: Boolean = false) =
        repository.getClinics(cityId, forceRefresh)
}

class SelectCityUseCase(
    private val clinics: ClinicRepository,
    private val selectedCity: SelectedCityRepository,
) {
    suspend operator fun invoke(cityId: String): AppResult<List<Clinic>> =
        clinics.getClinics(cityId).also { result ->
            if (result is AppResult.Success) selectedCity.save(cityId)
        }
}

val cityDomainModule = module {
    factory { SearchCitiesUseCase(get()) }
    factory { GetClinicsUseCase(get()) }
    factory { SelectCityUseCase(get(), get()) }
}
