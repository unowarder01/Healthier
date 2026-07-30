package unowarder01.healthier.features.auth.domain

import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.platform.SocialProvider

interface AuthRepository {
    suspend fun authenticate(provider: SocialProvider): AppResult<Unit>
}
