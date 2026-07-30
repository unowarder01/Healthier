package unowarder01.healthier.features.auth.di

import org.koin.dsl.module
import unowarder01.healthier.features.auth.domain.usecase.AuthenticateUseCase
import unowarder01.healthier.features.auth.domain.usecase.AuthenticateUseCaseImpl

val authDomainModule = module {
    factory<AuthenticateUseCase> { AuthenticateUseCaseImpl(get()) }
}
