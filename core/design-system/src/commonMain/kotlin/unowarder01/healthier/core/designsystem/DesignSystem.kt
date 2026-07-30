package unowarder01.healthier.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.*

object HealthierTokens {
    val radius = 16.dp
    val floatingElevation = 8.dp
    val accent = Color(0xFF276EF1)
    val telegram = Color(0xFF229ED9)
    val meta = Color(0xFF0866FF)
}

@Composable
fun HealthierTheme(
    theme: AppTheme,
    systemDark: Boolean,
    content: @Composable () -> Unit,
) {
    val dark = when (theme) {
        AppTheme.System -> systemDark
        AppTheme.Light -> false
        AppTheme.Dark -> true
    }
    val scheme: ColorScheme = if (dark) {
        darkColorScheme(primary = Color(0xFF9BBDF9), secondary = Color(0xFF7DD3C7))
    } else {
        lightColorScheme(
            primary = HealthierTokens.accent,
            background = Color(0xFFF8FAFD),
            surface = Color.White,
        )
    }
    MaterialTheme(colorScheme = scheme, content = content)
}

@Composable
fun AppLogo(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(HealthierTokens.floatingElevation, RoundedCornerShape(HealthierTokens.radius))
            .background(HealthierTokens.accent, RoundedCornerShape(HealthierTokens.radius)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "H",
            color = Color.White,
            fontSize = (size.value * 0.48f).sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

enum class TextKey {
    Auth, YourCity, Search, NoResults, TryAgain, Health, Map, Profile, HealthSearch,
    Stories, TopClinics, TopDoctors, Close, DemoMap, MapUnavailable, Documents, Settings,
    Social, Identity, Consents, Scans, ColorTheme, AppLanguage, Notifications, ComingSoon,
    NotConfigured, EditProfile, Name, Save, Avatar, ChangeLocation, ChangeLanguage,
    LanguageGeorgian, LanguageEnglish, LanguageRussian, ThemeSystem, ThemeLight, ThemeDark,
}

@Composable
fun appString(language: AppLanguage, key: TextKey): String =
    stringResource(resource(language, key))

private fun resource(language: AppLanguage, key: TextKey): StringResource = when (key) {
    TextKey.Auth -> pick(language, Res.string.auth_ka, Res.string.auth_en, Res.string.auth_ru)
    TextKey.YourCity -> pick(language, Res.string.your_city_ka, Res.string.your_city_en, Res.string.your_city_ru)
    TextKey.Search -> pick(language, Res.string.search_ka, Res.string.search_en, Res.string.search_ru)
    TextKey.NoResults -> pick(language, Res.string.no_results_ka, Res.string.no_results_en, Res.string.no_results_ru)
    TextKey.TryAgain -> pick(language, Res.string.try_again_ka, Res.string.try_again_en, Res.string.try_again_ru)
    TextKey.Health -> pick(language, Res.string.health_ka, Res.string.health_en, Res.string.health_ru)
    TextKey.Map -> pick(language, Res.string.map_ka, Res.string.map_en, Res.string.map_ru)
    TextKey.Profile -> pick(language, Res.string.profile_ka, Res.string.profile_en, Res.string.profile_ru)
    TextKey.HealthSearch -> pick(language, Res.string.health_search_ka, Res.string.health_search_en, Res.string.health_search_ru)
    TextKey.Stories -> pick(language, Res.string.stories_ka, Res.string.stories_en, Res.string.stories_ru)
    TextKey.TopClinics -> pick(language, Res.string.top_clinics_ka, Res.string.top_clinics_en, Res.string.top_clinics_ru)
    TextKey.TopDoctors -> pick(language, Res.string.top_doctors_ka, Res.string.top_doctors_en, Res.string.top_doctors_ru)
    TextKey.Close -> pick(language, Res.string.close_ka, Res.string.close_en, Res.string.close_ru)
    TextKey.DemoMap -> pick(language, Res.string.demo_map_ka, Res.string.demo_map_en, Res.string.demo_map_ru)
    TextKey.MapUnavailable -> pick(language, Res.string.map_unavailable_ka, Res.string.map_unavailable_en, Res.string.map_unavailable_ru)
    TextKey.Documents -> pick(language, Res.string.documents_ka, Res.string.documents_en, Res.string.documents_ru)
    TextKey.Settings -> pick(language, Res.string.settings_ka, Res.string.settings_en, Res.string.settings_ru)
    TextKey.Social -> pick(language, Res.string.social_ka, Res.string.social_en, Res.string.social_ru)
    TextKey.Identity -> pick(language, Res.string.identity_ka, Res.string.identity_en, Res.string.identity_ru)
    TextKey.Consents -> pick(language, Res.string.consents_ka, Res.string.consents_en, Res.string.consents_ru)
    TextKey.Scans -> pick(language, Res.string.scans_ka, Res.string.scans_en, Res.string.scans_ru)
    TextKey.ColorTheme -> pick(language, Res.string.color_theme_ka, Res.string.color_theme_en, Res.string.color_theme_ru)
    TextKey.AppLanguage -> pick(language, Res.string.app_language_ka, Res.string.app_language_en, Res.string.app_language_ru)
    TextKey.Notifications -> pick(language, Res.string.notifications_ka, Res.string.notifications_en, Res.string.notifications_ru)
    TextKey.ComingSoon -> pick(language, Res.string.coming_soon_ka, Res.string.coming_soon_en, Res.string.coming_soon_ru)
    TextKey.NotConfigured -> pick(language, Res.string.not_configured_ka, Res.string.not_configured_en, Res.string.not_configured_ru)
    TextKey.EditProfile -> pick(language, Res.string.edit_profile_ka, Res.string.edit_profile_en, Res.string.edit_profile_ru)
    TextKey.Name -> pick(language, Res.string.name_ka, Res.string.name_en, Res.string.name_ru)
    TextKey.Save -> pick(language, Res.string.save_ka, Res.string.save_en, Res.string.save_ru)
    TextKey.Avatar -> pick(language, Res.string.avatar_ka, Res.string.avatar_en, Res.string.avatar_ru)
    TextKey.ChangeLocation -> pick(
        language,
        Res.string.change_location_ka,
        Res.string.change_location_en,
        Res.string.change_location_ru,
    )
    TextKey.ChangeLanguage -> pick(
        language,
        Res.string.change_language_ka,
        Res.string.change_language_en,
        Res.string.change_language_ru,
    )
    TextKey.LanguageGeorgian -> Res.string.language_ka
    TextKey.LanguageEnglish -> Res.string.language_en
    TextKey.LanguageRussian -> Res.string.language_ru
    TextKey.ThemeSystem -> pick(
        language,
        Res.string.theme_system_ka,
        Res.string.theme_system_en,
        Res.string.theme_system_ru,
    )
    TextKey.ThemeLight -> pick(
        language,
        Res.string.theme_light_ka,
        Res.string.theme_light_en,
        Res.string.theme_light_ru,
    )
    TextKey.ThemeDark -> pick(
        language,
        Res.string.theme_dark_ka,
        Res.string.theme_dark_en,
        Res.string.theme_dark_ru,
    )
}

private fun pick(
    language: AppLanguage,
    ka: StringResource,
    en: StringResource,
    ru: StringResource,
): StringResource = when (language) {
    AppLanguage.Georgian -> ka
    AppLanguage.English -> en
    AppLanguage.Russian -> ru
}
