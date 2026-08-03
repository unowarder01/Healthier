package unowarder01.healthier.features.city.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.city.repository.CityRepository
import unowarder01.healthier.features.city.repository.CityRepositoryImpl

val cityDataModule = module {
    factoryOf(::CityRepositoryImpl) { bind<CityRepository>() }
}
