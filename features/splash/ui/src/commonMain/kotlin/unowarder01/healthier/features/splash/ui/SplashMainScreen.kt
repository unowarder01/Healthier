package unowarder01.healthier.features.splash.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppLanguage.English
import unowarder01.healthier.core.common.AppLanguage.Georgian
import unowarder01.healthier.core.common.AppLanguage.Russian
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_flag_ge
import unowarder01.healthier.designsystem.generated.resources.ic_flag_ru
import unowarder01.healthier.designsystem.generated.resources.ic_flag_uk
import unowarder01.healthier.designsystem.generated.resources.ic_splash_logo
import unowarder01.healthier.features.splash.ui.SplashContract.Listener
import unowarder01.healthier.features.splash.ui.SplashContract.State

@Composable
fun SplashMainScreen(
    state: State,
    listener: Listener
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E0E12))
    ) {
        LogoAndProgress(state)
        TitleAndLanguages(
            state = state,
            listener = listener
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
        color = Color(0xFF393943),
        strokeWidth = 2.dp
    )
}

/**
 * LANGUAGES
 */
@Composable
private fun BoxScope.TitleAndLanguages(
    state: State,
    listener: Listener
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
    ) {
        Text(
            text = "App language",
            style = typography.headlineMedium,
            color = Color(0xFFF7F7FA),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        Languages(
            state = state,
            listener = listener
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
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFB8B8C4) else Color.Transparent,
        animationSpec = tween()
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .height(56.dp)
            .clip(shapes.large)
            .background(
                color = Color(0xFF18181E),
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
                .size(28.dp)
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
