package unowarder01.healthier.features.splash.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.jetbrains.compose.resources.stringResource
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppLanguage.English
import unowarder01.healthier.core.common.AppLanguage.Georgian
import unowarder01.healthier.core.common.AppLanguage.Russian
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.core.designsystem.theme.HealthierTheme
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.app_language
import unowarder01.healthier.designsystem.generated.resources.ic_flag_ge
import unowarder01.healthier.designsystem.generated.resources.ic_flag_ru
import unowarder01.healthier.designsystem.generated.resources.ic_flag_uk
import unowarder01.healthier.designsystem.generated.resources.ic_splash_logo
import unowarder01.healthier.features.splash.ui.SplashContract.Listener
import unowarder01.healthier.features.splash.ui.SplashContract.State

private enum class SplashThemeMode(val label: String) {
    System("System"),
    Light("Light"),
    Dark("Dark")
}

@Composable
fun SplashMainScreen(
    state: State,
    listener: Listener
) {
    var themeMode by remember { mutableStateOf(SplashThemeMode.System) }
    val darkTheme = when (themeMode) {
        SplashThemeMode.System -> isSystemInDarkTheme()
        SplashThemeMode.Light -> false
        SplashThemeMode.Dark -> true
    }

    HealthierTheme(darkTheme = darkTheme) {
        SplashContent(
            state = state,
            listener = listener,
            themeMode = themeMode,
            onThemeSelected = { themeMode = it }
        )
    }
}

@Composable
private fun SplashContent(
    state: State,
    listener: Listener,
    themeMode: SplashThemeMode,
    onThemeSelected: (SplashThemeMode) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        LogoAndProgress(state)
        TitleAndSettings(
            state = state,
            listener = listener,
            themeMode = themeMode,
            onThemeSelected = onThemeSelected
        )
    }
}

/**
 * LOGO AND PROGRESS
 */
@Composable
private fun LogoAndProgress(state: State) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (logoRef, progressRef) = createRefs()
        SplashLogo(
            modifier = Modifier.constrainAs(logoRef) {
                centerTo(parent)
            }
        )
        AnimatedVisibility(
            visible = !state.showLanguagesContainer,
            enter = EnterTransition.None,
            exit = fadeOut(animationSpec = tween(durationMillis = 150)),
            modifier = Modifier
                .padding(bottom = 90.dp) // (LogoViewPortHeight - LogoHeight) / 2
                .constrainAs(progressRef) {
                    centerHorizontallyTo(parent)
                    top.linkTo(logoRef.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Progress()
        }
    }
}

@Composable
private fun SplashLogo(modifier: Modifier) {
    AppImage(
        image = Res.drawable.ic_splash_logo,
        modifier = modifier.testTag("splash_logo")
    )
}

@Composable
private fun Progress() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = colorScheme.onSurfaceVariant,
        strokeWidth = 2.dp
    )
}

/**
 * LANGUAGES
 */
@Composable
private fun BoxScope.TitleAndSettings(
    state: State,
    listener: Listener,
    themeMode: SplashThemeMode,
    onThemeSelected: (SplashThemeMode) -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (state.showLanguagesContainer) 0.dp else 300.dp,
        animationSpec = tween()
    )
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .navigationBarsPadding()
            .align(Alignment.BottomCenter)
            .offset(y = offset)
            .fillMaxWidth()
            .clip(shapes.large)
            .background(
                color = colorScheme.surface,
                shape = shapes.large
            )
            .border(
                width = 1.dp,
                color = colorScheme.outline,
                shape = shapes.large
            )
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 16.dp)
                .width(32.dp)
                .height(2.dp)
                .clip(shapes.small)
                .background(
                    color = colorScheme.outline,
                    shape = shapes.small
                )
        )
        Text(
            text = "Theme",
            style = typography.titleMedium,
            color = colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Themes(
            selectedTheme = themeMode,
            onThemeSelected = onThemeSelected
        )
        Text(
            text = "Language",
            style = typography.titleMedium,
            color = colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )
        Languages(
            state = state,
            listener = listener
        )
    }
}

@Composable
private fun Themes(
    selectedTheme: SplashThemeMode,
    onThemeSelected: (SplashThemeMode) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        SplashThemeMode.entries.forEach { theme ->
            Theme(
                theme = theme,
                isSelected = theme == selectedTheme,
                onClick = { onThemeSelected(theme) }
            )
        }
    }
}

@Composable
private fun RowScope.Theme(
    theme: SplashThemeMode,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) colorScheme.primary else colorScheme.outlineVariant
    val containerColor = if (isSelected) colorScheme.primaryContainer else colorScheme.surfaceContainerHigh
    val contentColor = if (isSelected) colorScheme.onPrimaryContainer else colorScheme.onSurfaceVariant
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .clip(shapes.large)
            .background(
                color = containerColor,
                shape = shapes.large
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.large
            )
            .clickable { onClick() }
            .testTag("theme_${theme.name.lowercase()}")
    ) {
        Text(
            text = theme.label,
            style = typography.labelMedium,
            color = contentColor
        )
    }
}

@Composable
private fun Languages(
    state: State,
    listener: Listener
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        AppLanguage.entries.forEach { language ->
            Language(
                language = language,
                isSelected = language == state.selectedLanguage,
                onClick = { listener.onLanguageClick(language) }
            )
        }
    }
}

@Composable
private fun RowScope.Language(
    language: AppLanguage,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) colorScheme.primary else colorScheme.outlineVariant
    val containerColor = if (isSelected) { colorScheme.primaryContainer } else { colorScheme.surfaceContainerHigh }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .clip(shapes.large)
            .background(
                color = containerColor,
                shape = shapes.large
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.large
            )
            .clickable { onClick() }
            .testTag("language_${language.code}")
    ) {
        AppImage(
            image = language.getCountryFlag(),
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .semantics { contentDescription = "${language.englishName} flag" }
        )
    }
}

// TODO: Make AppLanguageUi with DrawableResource
private fun AppLanguage.getCountryFlag() = when (this) {
    Georgian -> Res.drawable.ic_flag_ge
    English -> Res.drawable.ic_flag_uk
    Russian -> Res.drawable.ic_flag_ru
}
