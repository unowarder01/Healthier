package unowarder01.healthier.features.auth.domain

import org.koin.dsl.module
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.platform.SocialProvider

interface AuthRepository {
    suspend fun authenticate(provider: SocialProvider): AppResult<Unit>
}

class AuthenticateUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(provider: SocialProvider): AppResult<Unit> =
        repository.authenticate(provider)
}

val authDomainModule = module {
    factory { AuthenticateUseCase(get()) }
}
