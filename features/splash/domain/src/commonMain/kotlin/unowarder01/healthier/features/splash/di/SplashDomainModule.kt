package unowarder01.healthier.features.splash.di

import org.koin.dsl.module
import unowarder01.healthier.features.splash.domain.usecase.SelectLanguageUseCase
import unowarder01.healthier.features.splash.domain.usecase.SelectLanguageUseCaseImpl

val splashDomainModule = module {
    factory<SelectLanguageUseCase> { SelectLanguageUseCaseImpl(get()) }
}
