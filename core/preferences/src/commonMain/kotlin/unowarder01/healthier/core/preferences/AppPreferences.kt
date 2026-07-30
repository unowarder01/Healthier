package unowarder01.healthier.core.preferences

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme

interface SettingsRepository {
    val language: StateFlow<AppLanguage>
    val theme: StateFlow<AppTheme>
    val selectedCityId: StateFlow<String?>
    fun setLanguage(value: AppLanguage)
    fun setTheme(value: AppTheme)
    fun setSelectedCityId(value: String)
}

class SettingsRepositoryImpl(
    private val settings: Settings,
) : SettingsRepository {
    private val languageState = MutableStateFlow(AppLanguage.fromCode(settings.getStringOrNull(LANGUAGE)))
    private val themeState = MutableStateFlow(
        settings.getStringOrNull(THEME)?.let { saved ->
            AppTheme.entries.firstOrNull { it.name == saved }
        } ?: AppTheme.System
    )
    private val cityState = MutableStateFlow(settings.getStringOrNull(CITY))

    override val language: StateFlow<AppLanguage> = languageState.asStateFlow()
    override val theme: StateFlow<AppTheme> = themeState.asStateFlow()
    override val selectedCityId: StateFlow<String?> = cityState.asStateFlow()

    override fun setLanguage(value: AppLanguage) {
        settings.putString(LANGUAGE, value.code)
        languageState.value = value
    }

    override fun setTheme(value: AppTheme) {
        settings.putString(THEME, value.name)
        themeState.value = value
    }

    override fun setSelectedCityId(value: String) {
        settings.putString(CITY, value)
        cityState.value = value
    }

    private companion object {
        const val LANGUAGE = "app.language"
        const val THEME = "app.theme"
        const val CITY = "app.selected-city"
    }
}
