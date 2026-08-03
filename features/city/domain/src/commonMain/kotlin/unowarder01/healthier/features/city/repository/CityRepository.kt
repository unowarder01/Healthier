package unowarder01.healthier.features.city.repository

import unowarder01.healthier.features.city.model.CityDomain

interface CityRepository {
    suspend fun getCities(): List<CityDomain>
}