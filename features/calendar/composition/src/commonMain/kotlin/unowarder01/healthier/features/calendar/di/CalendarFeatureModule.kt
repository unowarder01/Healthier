package unowarder01.healthier.features.calendar.di

import org.koin.dsl.module

val calendarFeatureModule = module {
    includes(
        calendarDataModule,
        calendarDomainModule,
        calendarUiModule,
    )
}
