package unowarder01.healthier.features.map.di

import org.koin.dsl.module

val mapFeatureModule = module {
    includes(mapDomainModule, mapDataModule, mapUiModule)
}
