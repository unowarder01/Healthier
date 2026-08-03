package unowarder01.healthier.features.city.ui.mapper

import unowarder01.healthier.core.common.mapper.Mapper
import unowarder01.healthier.features.city.model.CityDomain
import unowarder01.healthier.features.city.model.CityDomainStatus.READY
import unowarder01.healthier.features.city.model.CityDomainStatus.SOON
import unowarder01.healthier.features.city.ui.data.CityUi
import unowarder01.healthier.features.city.ui.data.CityUi.ReadyCityUi
import unowarder01.healthier.features.city.ui.data.CityUi.SoonCityUi

interface CityUiMapper: Mapper<CityDomain, CityUi>

internal class CityUiMapperImpl: CityUiMapper {
    override fun map(from: CityDomain) = when (from.status) {
        SOON -> SoonCityUi(
            id = from.id,
            name = from.name,
        )
        READY -> ReadyCityUi(
            id = from.id,
            name = from.name,
            doctorsCount = (50..100).random(),
            clinicsCount = (1..50).random()
        )
    }
}