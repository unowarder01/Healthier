package unowarder01.healthier.features.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.AppLogo
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.features.auth.ui.AuthContract.Listener
import unowarder01.healthier.core.platform.SocialProvider

@Composable
fun AuthMainScreen(
    state: AuthContract.State,
    listener: Listener,
    providers: Set<SocialProvider>,
    language: AppLanguage
) {
    LaunchedEffect(Unit) {
        delay(500)
        listener.onScreenShown()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = HealthierTokens.pageHorizontalPadding)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(28.dp))
            AppLogo(
                size = 64.dp,
                modifier = Modifier.testTag("auth_logo")
            )
            Spacer(Modifier.size(20.dp))
            Text(
                text = "Healthier",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = appString(language, TextKey.Auth),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AnimatedVisibility(
            visible = state.visible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = fadeIn(tween(180)) + slideInVertically(
                animationSpec = tween(340, easing = FastOutSlowInEasing),
                initialOffsetY = { it / 3 }
            )
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = HealthierTokens.floatingElevation
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = appString(language, TextKey.Auth),
                        style = MaterialTheme.typography.titleLarge
                    )
                    providers.forEach { provider ->
                        ProviderButton(
                            provider = provider,
                            language = language,
                            loading = state.loadingProvider == provider,
                            enabled = state.loadingProvider == null,
                            onClick = { listener.onProviderSelected(provider) }
                        )
                    }
                    state.error?.let {
                        Text(
                            text = appString(language, TextKey.NotConfigured),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("auth_error")
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProviderButton(
    provider: SocialProvider,
    language: AppLanguage,
    loading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val label = "${appString(language, TextKey.ContinueWith)} ${provider.name}"
    val modifier = Modifier
        .fillMaxWidth()
        .testTag("auth_${provider.name.lowercase()}")

    when (provider) {
        SocialProvider.Apple -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF171717),
                contentColor = Color.White
            )
        ) {
            ProviderButtonContent(label, "●", loading)
        }

        SocialProvider.Google -> OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            ProviderButtonContent(label, "G", loading)
        }

        SocialProvider.Meta -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = HealthierTokens.meta)
        ) {
            ProviderButtonContent(label, "f", loading)
        }

        SocialProvider.Telegram -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = HealthierTokens.telegram)
        ) {
            ProviderButtonContent(label, "✈", loading)
        }
    }
}

@Composable
private fun ProviderButtonContent(
    label: String,
    mark: String,
    loading: Boolean
) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 2.dp
        )
        return
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = mark,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(end = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}
