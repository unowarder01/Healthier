package unowarder01.healthier.navigation

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import ui.OnboardingNavigator
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.preferences.SettingsRepository
import unowarder01.healthier.features.auth.ui.AuthNavigator
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.city.ui.ChooseCityNavigator
import unowarder01.healthier.features.health.ui.HealthNavigator
import unowarder01.healthier.features.map.ui.MapNavigator
import unowarder01.healthier.features.profile.domain.Profile
import unowarder01.healthier.features.profile.ui.ProfileContract.Message
import unowarder01.healthier.features.profile.ui.ProfileNavigator
import unowarder01.healthier.features.splash.ui.SplashNavigator
import unowarder01.healthier.navigation.dialogs.AppDialogsNavigator
import unowarder01.healthier.navigation.screens.AppScreensConfig

class SplashNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>
) : SplashNavigator {
    override fun toAuth() {
        navigation.replaceAll(AppScreensConfig.AuthConfig)
    }

    override fun toOnboarding() {
        navigation.replaceAll(AppScreensConfig.OnboardingConfig)
    }
}

class OnboardingNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>
): OnboardingNavigator {
    override fun toAuth() {
        navigation.replaceAll(AppScreensConfig.AuthConfig)
    }
}

class AuthNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>
) : AuthNavigator {
    override fun openCity() {
        navigation.replaceCurrent(AppScreensConfig.CityConfig)
    }
}

class ChooseCityNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>
) : ChooseCityNavigator {
    override fun openHome(clinics: List<Clinic>) {
        navigation.replaceAll(AppScreensConfig.HomeConfig(clinics))
    }
}

class HealthNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>,
    private val settings: SettingsRepository
) : HealthNavigator {
    override fun changeLocation() {
        navigation.replaceAll(AppScreensConfig.CityConfig)
    }

    override fun changeLanguage() {
        val languages = AppLanguage.entries
        val currentIndex = languages.indexOf(settings.language.value)
        settings.setLanguage(languages[(currentIndex + 1) % languages.size])
    }
}

class MapNavigatorImpl : MapNavigator

class ProfileNavigatorImpl(
    private val navigation: StackNavigation<AppScreensConfig>,
    private val dialogs: AppDialogsNavigator
) : ProfileNavigator {
    override fun changeLocation() {
        navigation.replaceAll(AppScreensConfig.CityConfig)
    }

    override fun showProfileEditor(
        profile: Profile,
        language: AppLanguage,
        onSave: (name: String, avatarReference: String?) -> Unit
    ) {
        dialogs.showProfileEditor(profile, language, onSave)
    }

    override fun showLanguageSelector(
        language: AppLanguage,
        onSelect: (AppLanguage) -> Unit
    ) {
        dialogs.showLanguageSelector(language, onSelect)
    }

    override fun showThemeSelector(
        language: AppLanguage,
        theme: AppTheme,
        onSelect: (AppTheme) -> Unit
    ) {
        dialogs.showThemeSelector(language, theme, onSelect)
    }

    override fun showMessage(
        language: AppLanguage,
        message: Message
    ) {
        dialogs.showMessage(language, message)
    }
}
