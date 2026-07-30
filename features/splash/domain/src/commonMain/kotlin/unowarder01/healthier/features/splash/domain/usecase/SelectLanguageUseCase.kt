package unowarder01.healthier.features.splash.domain.usecase

import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.core.preferences.SettingsRepository

interface SelectLanguageUseCase : BaseUseCase<AppLanguage, Unit>

internal class SelectLanguageUseCaseImpl(
    private val settings: SettingsRepository
) : SelectLanguageUseCase {
    override suspend fun invoke(params: AppLanguage) {
        settings.setLanguage(params)
    }
}
