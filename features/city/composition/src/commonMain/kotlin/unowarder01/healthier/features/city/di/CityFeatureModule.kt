package unowarder01.healthier.features.city.di

import org.koin.dsl.module

val cityFeatureModule = module {
    includes(
        cityDataModule,
        cityDomainModule,
        cityUiModule,
    )
}
