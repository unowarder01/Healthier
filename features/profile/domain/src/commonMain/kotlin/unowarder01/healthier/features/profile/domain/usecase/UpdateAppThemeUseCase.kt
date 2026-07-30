package unowarder01.healthier.features.profile.domain.usecase

import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.common.usecase.BaseUseCase
import unowarder01.healthier.core.preferences.SettingsRepository

interface UpdateAppThemeUseCase : BaseUseCase<AppTheme, Unit>

internal class UpdateAppThemeUseCaseImpl(
    private val settings: SettingsRepository
) : UpdateAppThemeUseCase {
    override suspend fun invoke(params: AppTheme) {
        settings.setTheme(params)
    }
}
