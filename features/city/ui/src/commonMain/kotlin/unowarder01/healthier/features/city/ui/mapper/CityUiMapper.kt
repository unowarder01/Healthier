package unowarder01.healthier.features.city.ui.mapper

import unowarder01.healthier.core.common.mapper.Mapper
import unowarder01.healthier.features.city.model.CityDomain
import unowarder01.healthier.features.city.ui.data.CityUi
import unowarder01.healthier.features.city.ui.data.CityUiStatus.READY
import unowarder01.healthier.features.city.ui.data.CityUiStatus.SOON

interface CityUiMapper: Mapper<CityDomain, CityUi>

internal class CityUiMapperImpl: CityUiMapper {
    override fun map(from: CityDomain) = CityUi(
        id = from.id,
        name = from.name,
        clinicsCount = (1..50).random(),
        doctorsCount = (50..100).random(),
        status = if (from.id in listOf(1, 2, 3)) READY else SOON
    )
}