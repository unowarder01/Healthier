package unowarder01.healthier.features.health.di

import org.koin.dsl.module

val healthFeatureModule = module {
    includes(healthDomainModule, healthDataModule, healthUiModule)
}
