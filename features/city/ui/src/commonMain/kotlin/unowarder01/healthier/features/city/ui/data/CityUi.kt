package unowarder01.healthier.features.city.ui.data

sealed class CityUi(
    open val id: Int,
    open val name: String
) {
    data class ReadyCityUi(
        override val id: Int,
        override val name: String,
        val doctorsCount: Int,
        val clinicsCount: Int
    ): CityUi(id, name)

    data class SoonCityUi(
        override val id: Int,
        override val name: String
    ): CityUi(id, name)
}