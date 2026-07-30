package unowarder01.healthier.features.home.composition

import org.koin.dsl.module
import unowarder01.healthier.features.home.data.homeDataModule
import unowarder01.healthier.features.home.domain.homeDomainModule
import unowarder01.healthier.features.home.ui.homeUiModule

val homeFeatureModule = module {
    includes(homeDomainModule, homeDataModule, homeUiModule)
}
