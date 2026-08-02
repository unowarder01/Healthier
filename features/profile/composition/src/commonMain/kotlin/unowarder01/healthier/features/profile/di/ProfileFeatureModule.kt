package unowarder01.healthier.features.profile.di

import org.koin.dsl.module

val profileFeatureModule = module {
    includes(
        profileDataModule,
        profileDomainModule,
        profileUiModule,
    )
}
