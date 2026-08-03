package unowarder01.healthier.features.city.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.city.usecase.GetCitiesUseCase
import unowarder01.healthier.features.city.usecase.GetCitiesUseCaseImpl

val cityDomainModule = module {
    factoryOf(::GetCitiesUseCaseImpl) { bind<GetCitiesUseCase>() }
}
