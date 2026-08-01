package unowarder01.healthier.features.splash.di

import org.koin.dsl.module

val splashFeatureModule = module {
    includes(splashUiModule)
}
