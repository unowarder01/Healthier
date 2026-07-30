package unowarder01.healthier.features.profile.domain.usecase

import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.features.profile.domain.ProfileRepository

data class UpdateProfileParams(
    val name: String,
    val avatarReference: String?
)

interface UpdateProfileUseCase : BaseUseCase<UpdateProfileParams, Unit>

internal class UpdateProfileUseCaseImpl(
    private val repository: ProfileRepository
) : UpdateProfileUseCase {
    override suspend fun invoke(params: UpdateProfileParams) {
        repository.update(
            name = params.name.trim(),
            avatarReference = params.avatarReference
        )
    }
}
