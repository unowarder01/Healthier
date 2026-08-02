package unowarder01.healthier.di

import onboardingFeatureModule
import unowarder01.healthier.features.auth.di.authFeatureModule
import unowarder01.healthier.features.city.di.cityFeatureModule
import unowarder01.healthier.features.splash.di.splashFeatureModule

val appModules = listOf(
    /**
     * START
     */
    splashFeatureModule,
    onboardingFeatureModule,
    authFeatureModule,
    cityFeatureModule,
    /**
     * NAVIGATORS
     */
    appNavigatorsModule
)