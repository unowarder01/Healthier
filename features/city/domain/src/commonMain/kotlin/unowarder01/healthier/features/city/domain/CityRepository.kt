package unowarder01.healthier.features.city.domain

import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun observeCities(): Flow<List<City>>
    suspend fun searchCities(query: String): List<City>
}
