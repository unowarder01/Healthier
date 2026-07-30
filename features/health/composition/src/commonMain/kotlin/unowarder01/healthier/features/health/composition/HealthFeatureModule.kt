package unowarder01.healthier.features.health.composition

import org.koin.dsl.module
import unowarder01.healthier.features.health.data.healthDataModule
import unowarder01.healthier.features.health.domain.healthDomainModule
import unowarder01.healthier.features.health.ui.healthUiModule

val healthFeatureModule = module {
    includes(healthDomainModule, healthDataModule, healthUiModule)
}
