package unowarder01.healthier.features.splash.composition

import org.koin.dsl.module
import unowarder01.healthier.features.splash.data.splashDataModule
import unowarder01.healthier.features.splash.domain.splashDomainModule
import unowarder01.healthier.features.splash.ui.splashUiModule

val splashFeatureModule = module {
    includes(splashDomainModule, splashDataModule, splashUiModule)
}
