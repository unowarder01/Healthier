package unowarder01.healthier.features.auth.domain.usecase

import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.core.platform.SocialProvider
import unowarder01.healthier.features.auth.domain.AuthRepository

interface AuthenticateUseCase : BaseUseCase<SocialProvider, AppResult<Unit>>

internal class AuthenticateUseCaseImpl(
    private val repository: AuthRepository
) : AuthenticateUseCase {
    override suspend fun invoke(params: SocialProvider): AppResult<Unit> =
        repository.authenticate(params)
}
