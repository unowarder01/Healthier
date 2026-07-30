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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.AppLogo
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.features.splash.ui.SplashContract.Listener
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashMainScreen(
    state: SplashContract.State,
    listener: Listener
) {
    var showLanguages by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AnimatedVisibility(
            visible = !state.exiting,
            exit = fadeOut(tween(180))
        ) {
            AppLogo(
                size = 96.dp,
                modifier = Modifier.testTag("splash_logo")
            )
        }
        AnimatedVisibility(
            visible = showLanguages && !state.exiting,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(220)
            )
        ) {
            Card(
                shape = RoundedCornerShape(HealthierTokens.radius),
                elevation = CardDefaults.cardElevation(HealthierTokens.floatingElevation),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .testTag("language_container")
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    AppLanguage.entries.forEach { language ->
                        LanguageRow(
                            language = language,
                            isClickable = !state.exiting,
                            isSelected = language == state.selected,
                            onClick = {
                                listener.onLanguageSelected(language)
                            }
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        showLanguages = true
    }
}

@Composable
private fun LanguageRow(
    language: AppLanguage,
    isSelected: Boolean,
    isClickable: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isClickable) { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("language_${language.code}")
    ) {
        Text(
            text = language.flag,
            modifier = Modifier
                .size(32.dp)
                .semantics { contentDescription = "${language.englishName} flag" }
        )
        Text(
            text = language.nativeName,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            modifier = Modifier.alpha(if (isSelected) 1f else 0.9f)
        )
    }
}
