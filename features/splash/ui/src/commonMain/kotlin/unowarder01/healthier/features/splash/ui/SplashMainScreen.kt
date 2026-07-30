package unowarder01.healthier.features.splash.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.AppLogo
import unowarder01.healthier.core.designsystem.HealthierTokens

@Composable
fun SplashMainScreen(component: SplashComponent) = with(component.store) {
    val state by subscribe { component.handle(it) }

    LaunchedEffect(Unit) {
        delay(500)
        intent(SplashContract.Intent.RevealLanguages)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = !state.exiting,
            exit = fadeOut(tween(180)),
        ) {
            AppLogo(size = 96.dp, modifier = Modifier.testTag("splash_logo"))
        }

        AnimatedVisibility(
            visible = state.showLanguages && !state.exiting,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing),
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(220),
            ),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .testTag("language_container"),
                shape = RoundedCornerShape(HealthierTokens.radius),
                elevation = CardDefaults.cardElevation(HealthierTokens.floatingElevation),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Column(Modifier.padding(vertical = 8.dp)) {
                    LanguageRow("🇬🇪", "ქართული", "Georgian", AppLanguage.Georgian, state) {
                        intent(SplashContract.Intent.SelectLanguage(it))
                    }
                    LanguageRow("🇬🇧", "English", "English", AppLanguage.English, state) {
                        intent(SplashContract.Intent.SelectLanguage(it))
                    }
                    LanguageRow("🇷🇺", "Русский", "Russian", AppLanguage.Russian, state) {
                        intent(SplashContract.Intent.SelectLanguage(it))
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageRow(
    flag: String,
    name: String,
    description: String,
    language: AppLanguage,
    state: SplashContract.State,
    onSelect: (AppLanguage) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !state.exiting) { onSelect(language) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("language_${language.code}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = flag,
            modifier = Modifier
                .size(32.dp)
                .semantics { contentDescription = "$description flag" },
        )
        Text(name, modifier = Modifier.weight(1f))
        RadioButton(
            selected = state.selected == language,
            onClick = null,
            modifier = Modifier.alpha(if (state.selected == language) 1f else 0.9f),
        )
    }
}
