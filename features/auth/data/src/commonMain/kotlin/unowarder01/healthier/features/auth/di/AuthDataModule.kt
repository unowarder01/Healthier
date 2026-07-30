package unowarder01.healthier.features.auth.di

import org.koin.dsl.module
import unowarder01.healthier.core.network.NetworkEnvironment
import unowarder01.healthier.core.platform.SocialAuthProvider
import unowarder01.healthier.core.platform.currentPlatformKind
import unowarder01.healthier.core.platform.MemorySecureStorage
import unowarder01.healthier.core.platform.SecureStorage
import unowarder01.healthier.features.auth.data.AuthRepositoryImpl
import unowarder01.healthier.features.auth.data.DemoSocialAuthProvider
import unowarder01.healthier.features.auth.data.UnconfiguredSocialAuthProvider
import unowarder01.healthier.features.auth.domain.AuthRepository

val authDataModule = module {
    single<SecureStorage> { MemorySecureStorage() }
    single<SocialAuthProvider> {
        if (get<NetworkEnvironment>().isDebug) {
            DemoSocialAuthProvider(currentPlatformKind)
        } else {
            UnconfiguredSocialAuthProvider(currentPlatformKind)
        }
    }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
