package unowarder01.healthier.features.city.ui.mapper

import unowarder01.healthier.core.common.mapper.Mapper
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.other
import unowarder01.healthier.designsystem.generated.resources.popular
import unowarder01.healthier.features.city.model.CityDomain
import unowarder01.healthier.features.city.ui.data.CitiesUi

interface CitiesUiMapper: Mapper<List<CityDomain>, List<CitiesUi>>

internal class CitiesUiMapperImpl(
    private val cityUiMapper: CityUiMapper
): CitiesUiMapper {
    override fun map(from: List<CityDomain>): List<CitiesUi> {
        val sortedCities = from
            .sortedByDescending { city -> city.population }
            .map(cityUiMapper::map)
        return listOf(
            CitiesUi(
                header = Res.string.popular,
                items = sortedCities.take(3)
            ),
            CitiesUi(
                header = Res.string.other,
                items = sortedCities.takeLast(sortedCities.size - 3)
            )
        )
    }
}