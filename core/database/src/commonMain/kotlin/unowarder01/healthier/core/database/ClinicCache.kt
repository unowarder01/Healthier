package unowarder01.healthier.core.database

data class ClinicCacheRecord(
    val id: String,
    val cityId: String,
    val name: String,
    val specialization: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String?,
)

interface ClinicCache {
    suspend fun clinics(cityId: String): List<ClinicCacheRecord>
    suspend fun replace(cityId: String, clinics: List<ClinicCacheRecord>)
}

class InMemoryClinicCache : ClinicCache {
    private val values = mutableMapOf<String, List<ClinicCacheRecord>>()

    override suspend fun clinics(cityId: String): List<ClinicCacheRecord> =
        values[cityId].orEmpty()

    override suspend fun replace(cityId: String, clinics: List<ClinicCacheRecord>) {
        values[cityId] = clinics.toList()
    }
}
