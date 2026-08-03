package unowarder01.healthier.core.common.di

import org.koin.dsl.module
import unowarder01.healthier.core.common.dispatchers.AppDispatchers
import unowarder01.healthier.core.common.dispatchers.AppDispatchersImpl

val coreCommonModule = module {
    single<AppDispatchers> { AppDispatchersImpl() }
}