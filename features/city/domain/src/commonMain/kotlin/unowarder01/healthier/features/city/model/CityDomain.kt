package unowarder01.healthier.features.city.model

data class CityDomain(
    val id: Int,
    val name: String,
    val population: Int,
    val status: CityDomainStatus
)

enum class CityDomainStatus {
    READY,
    SOON
}