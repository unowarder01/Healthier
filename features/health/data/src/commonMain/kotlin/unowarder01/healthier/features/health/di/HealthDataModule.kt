package unowarder01.healthier.features.health.di

import org.koin.dsl.module
import unowarder01.healthier.features.health.data.DemoHealthRepository
import unowarder01.healthier.features.health.domain.HealthRepository

val healthDataModule = module {
    single<HealthRepository> { DemoHealthRepository() }
}
