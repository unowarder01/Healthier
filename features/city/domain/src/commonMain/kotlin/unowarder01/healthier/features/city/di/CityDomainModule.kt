package unowarder01.healthier.features.city.di

import org.koin.dsl.module
import unowarder01.healthier.features.city.domain.usecase.GetClinicsUseCase
import unowarder01.healthier.features.city.domain.usecase.GetClinicsUseCaseImpl
import unowarder01.healthier.features.city.domain.usecase.SearchCitiesUseCase
import unowarder01.healthier.features.city.domain.usecase.SearchCitiesUseCaseImpl
import unowarder01.healthier.features.city.domain.usecase.SelectCityUseCase
import unowarder01.healthier.features.city.domain.usecase.SelectCityUseCaseImpl

val cityDomainModule = module {
    factory<SearchCitiesUseCase> { SearchCitiesUseCaseImpl(get()) }
    factory<GetClinicsUseCase> { GetClinicsUseCaseImpl(get()) }
    factory<SelectCityUseCase> { SelectCityUseCaseImpl(get(), get()) }
}
