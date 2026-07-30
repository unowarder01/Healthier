package unowarder01.healthier.features.city.di

import org.koin.dsl.module
import unowarder01.healthier.core.network.NetworkEnvironment
import unowarder01.healthier.features.city.data.ClinicRemoteSource
import unowarder01.healthier.features.city.data.ClinicRepositoryImpl
import unowarder01.healthier.features.city.data.CityRepositoryImpl
import unowarder01.healthier.features.city.data.DemoClinicRemoteSource
import unowarder01.healthier.features.city.data.KtorClinicRemoteSource
import unowarder01.healthier.features.city.data.SelectedCityRepositoryImpl
import unowarder01.healthier.features.city.domain.CityRepository
import unowarder01.healthier.features.city.domain.ClinicRepository
import unowarder01.healthier.features.city.domain.SelectedCityRepository

val cityDataModule = module {
    single<CityRepository> { CityRepositoryImpl() }
    single<ClinicRemoteSource> {
        if (get<NetworkEnvironment>().isDebug) DemoClinicRemoteSource()
        else KtorClinicRemoteSource(get(), get())
    }
    single<ClinicRepository> { ClinicRepositoryImpl(get(), get()) }
    single<SelectedCityRepository> { SelectedCityRepositoryImpl(get()) }
}
