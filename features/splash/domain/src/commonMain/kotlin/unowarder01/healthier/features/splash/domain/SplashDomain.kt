package unowarder01.healthier.features.splash.domain

import org.koin.dsl.module
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.preferences.SettingsRepository

class SelectLanguageUseCase(
    private val settings: SettingsRepository,
) {
    operator fun invoke(language: AppLanguage) = settings.setLanguage(language)
}

val splashDomainModule = module {
    factory { SelectLanguageUseCase(get()) }
}
