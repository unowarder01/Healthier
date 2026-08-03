package unowarder01.healthier.di

import onboardingFeatureModule
import unowarder01.healthier.core.common.di.coreCommonModule
import unowarder01.healthier.features.auth.di.authFeatureModule
import unowarder01.healthier.features.calendar.di.calendarFeatureModule
import unowarder01.healthier.features.city.di.cityFeatureModule
import unowarder01.healthier.features.health.di.healthFeatureModule
import unowarder01.healthier.features.map.di.mapFeatureModule
import unowarder01.healthier.features.profile.di.profileFeatureModule
import unowarder01.healthier.features.splash.di.splashFeatureModule

val appModules = listOf(
    /**
     * CORE
     */
    coreCommonModule,
    /**
     * START
     */
    splashFeatureModule,
    onboardingFeatureModule,
    authFeatureModule,
    cityFeatureModule,
    /**
     * MAIN
     */
    healthFeatureModule,
    mapFeatureModule,
    calendarFeatureModule,
    profileFeatureModule,
    /**
     * NAVIGATORS
     */
    appNavigatorsModule
)
