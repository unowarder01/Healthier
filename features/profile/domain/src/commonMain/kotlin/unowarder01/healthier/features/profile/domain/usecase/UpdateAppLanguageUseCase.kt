package unowarder01.healthier.features.profile.domain.usecase

import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.core.preferences.SettingsRepository

interface UpdateAppLanguageUseCase : BaseUseCase<AppLanguage, Unit>

internal class UpdateAppLanguageUseCaseImpl(
    private val settings: SettingsRepository
) : UpdateAppLanguageUseCase {
    override suspend fun invoke(params: AppLanguage) {
        settings.setLanguage(params)
    }
}
