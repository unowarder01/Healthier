package unowarder01.healthier.features.city.ui.data

data class CityUi(
    val id: Int,
    val name: String,
    val doctorsCount: Int,
    val clinicsCount: Int,
    val status: CityUiStatus
)

enum class CityUiStatus {
    READY,
    SOON
}