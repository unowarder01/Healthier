package unowarder01.healthier.features.auth.data

import kotlinx.coroutines.delay
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.network.NetworkEnvironment
import unowarder01.healthier.core.platform.AuthToken
import unowarder01.healthier.core.platform.MemorySecureStorage
import unowarder01.healthier.core.platform.PlatformKind
import unowarder01.healthier.core.platform.SecureStorage
import unowarder01.healthier.core.platform.SocialAuthProvider
import unowarder01.healthier.core.platform.SocialProvider
import unowarder01.healthier.core.platform.currentPlatformKind
import unowarder01.healthier.features.auth.domain.AuthRepository

class AuthRepositoryImpl(
    private val provider: SocialAuthProvider,
    private val secureStorage: SecureStorage
) : AuthRepository {
    override suspend fun authenticate(provider: SocialProvider): AppResult<Unit> =
        when (val result = this.provider.authenticate(provider)) {
            is AppResult.Success -> {
                secureStorage.saveAuthToken(result.value)
                AppResult.Success(Unit)
            }
            is AppResult.Failure -> result
        }
}

class DemoSocialAuthProvider(
    platform: PlatformKind
) : SocialAuthProvider {
    override val availableProviders: Set<SocialProvider> = buildSet {
        if (platform == PlatformKind.IOS) add(SocialProvider.Apple)
        if (platform != PlatformKind.Web) {
            add(SocialProvider.Google)
            add(SocialProvider.Meta)
            add(SocialProvider.Telegram)
        }
    }

    override suspend fun authenticate(provider: SocialProvider): AppResult<AuthToken> {
        if (provider !in availableProviders) return AppResult.Failure(AppError.NotConfigured)
        delay(350)
        return AppResult.Success(AuthToken("demo-session-in-memory"))
    }
}

class UnconfiguredSocialAuthProvider(
    platform: PlatformKind
) : SocialAuthProvider {
    override val availableProviders: Set<SocialProvider> = when (platform) {
        PlatformKind.IOS -> setOf(
            SocialProvider.Apple,
            SocialProvider.Google,
            SocialProvider.Meta,
            SocialProvider.Telegram
        )
        PlatformKind.Android -> setOf(
            SocialProvider.Google,
            SocialProvider.Meta,
            SocialProvider.Telegram
        )
        PlatformKind.Web -> emptySet()
    }

    override suspend fun authenticate(provider: SocialProvider): AppResult<AuthToken> =
        AppResult.Failure(AppError.NotConfigured)
}
