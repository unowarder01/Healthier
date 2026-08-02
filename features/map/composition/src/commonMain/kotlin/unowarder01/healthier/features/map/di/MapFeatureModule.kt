package unowarder01.healthier.features.map.di

import org.koin.dsl.module

val mapFeatureModule = module {
    includes(
        mapDataModule,
        mapDomainModule,
        mapUiModule,
    )
}
