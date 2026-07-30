package unowarder01.healthier.features.profile.di

import org.koin.dsl.module
import unowarder01.healthier.features.profile.data.DemoProfileRepository
import unowarder01.healthier.features.profile.domain.ProfileRepository

val profileDataModule = module {
    single<ProfileRepository> { DemoProfileRepository() }
}
