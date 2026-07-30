package unowarder01.healthier.features.city.domain

data class Clinic(
    val id: String,
    val cityId: String,
    val name: String,
    val specialization: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String?
)
