package unowarder01.healthier.features.health.di

import org.koin.dsl.module

val healthFeatureModule = module {
    includes(
        healthDataModule,
        healthDomainModule,
        healthUiModule,
    )
}
