package unowarder01.healthier.features.city.composition

import org.koin.dsl.module
import unowarder01.healthier.features.city.data.cityDataModule
import unowarder01.healthier.features.city.domain.cityDomainModule
import unowarder01.healthier.features.city.ui.cityUiModule

val cityFeatureModule = module {
    includes(cityDomainModule, cityDataModule, cityUiModule)
}
