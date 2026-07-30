package unowarder01.healthier.features.profile.di

import org.koin.dsl.module
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppLanguageUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppLanguageUseCaseImpl
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppThemeUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateAppThemeUseCaseImpl
import unowarder01.healthier.features.profile.domain.usecase.UpdateProfileUseCase
import unowarder01.healthier.features.profile.domain.usecase.UpdateProfileUseCaseImpl

val profileDomainModule = module {
    factory<UpdateProfileUseCase> { UpdateProfileUseCaseImpl(get()) }
    factory<UpdateAppLanguageUseCase> { UpdateAppLanguageUseCaseImpl(get()) }
    factory<UpdateAppThemeUseCase> { UpdateAppThemeUseCaseImpl(get()) }
}
