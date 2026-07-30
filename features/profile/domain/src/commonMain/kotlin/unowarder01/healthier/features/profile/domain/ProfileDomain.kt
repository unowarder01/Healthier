package unowarder01.healthier.features.profile.domain

import kotlinx.coroutines.flow.StateFlow
import org.koin.dsl.module
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.preferences.SettingsRepository

data class Profile(
    val name: String,
    val avatarReference: String?,
)

interface ProfileRepository {
    val profile: StateFlow<Profile>
    suspend fun update(name: String, avatarReference: String?)
}

class UpdateProfileUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(name: String, avatarReference: String?) =
        repository.update(name.trim(), avatarReference)
}

class UpdateAppLanguageUseCase(private val settings: SettingsRepository) {
    operator fun invoke(language: AppLanguage) = settings.setLanguage(language)
}

class UpdateAppThemeUseCase(private val settings: SettingsRepository) {
    operator fun invoke(theme: AppTheme) = settings.setTheme(theme)
}

val profileDomainModule = module {
    factory { UpdateProfileUseCase(get()) }
    factory { UpdateAppLanguageUseCase(get()) }
    factory { UpdateAppThemeUseCase(get()) }
}
