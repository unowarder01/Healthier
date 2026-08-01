package unowarder01.healthier.features.auth.di

import org.koin.dsl.module

val authFeatureModule = module {
    includes(authUiModule)
}
