package unowarder01.healthier.features.health.di

import org.koin.dsl.module
import unowarder01.healthier.features.health.domain.usecase.GetHealthContentUseCase
import unowarder01.healthier.features.health.domain.usecase.GetHealthContentUseCaseImpl

val healthDomainModule = module {
    factory<GetHealthContentUseCase> { GetHealthContentUseCaseImpl(get()) }
}
