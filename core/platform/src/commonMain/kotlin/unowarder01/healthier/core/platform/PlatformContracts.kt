package unowarder01.healthier.core.platform

import unowarder01.healthier.core.common.AppResult

enum class PlatformKind {
    Android,
    IOS,
    Web,
}

expect val currentPlatformKind: PlatformKind

enum class SocialProvider {
    Apple,
    Google,
    Meta,
    Telegram,
}

data class AuthToken(val value: String)

interface SocialAuthProvider {
    val availableProviders: Set<SocialProvider>
    suspend fun authenticate(provider: SocialProvider): AppResult<AuthToken>
}

interface SecureStorage {
    suspend fun saveAuthToken(token: AuthToken)
    suspend fun clearAuthToken()
}

interface PhotoPicker {
    suspend fun pickAvatar(): AppResult<String>
}

interface ExternalUrlLauncher {
    suspend fun open(url: String): AppResult<Unit>
}

enum class MapAvailability {
    Native,
    Demo,
    Unavailable,
}

interface MapRenderer {
    val availability: MapAvailability
}

class MemorySecureStorage : SecureStorage {
    private var token: AuthToken? = null

    override suspend fun saveAuthToken(token: AuthToken) {
        this.token = token
    }

    override suspend fun clearAuthToken() {
        token = null
    }
}
