package unowarder01.healthier.features.map.composition

import org.koin.dsl.module
import unowarder01.healthier.features.map.data.mapDataModule
import unowarder01.healthier.features.map.domain.mapDomainModule
import unowarder01.healthier.features.map.ui.mapUiModule

val mapFeatureModule = module {
    includes(mapDomainModule, mapDataModule, mapUiModule)
}
