package unowarder01.healthier.features.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.AppLogo
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.core.platform.SocialProvider

@Composable
fun AuthMainScreen(
    component: AuthComponent,
    language: AppLanguage,
) = with(component.store) {
    val state by subscribe { component.handle(it) }
    LaunchedEffect(Unit) {
        delay(500)
        intent(AuthContract.Intent.Reveal)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(16.dp),
    ) {
        AppLogo(56.dp, Modifier.align(Alignment.TopCenter).testTag("auth_logo"))

        AnimatedVisibility(
            visible = state.visible,
            modifier = Modifier.align(Alignment.Center),
            enter = slideInVertically(initialOffsetY = { -it }),
        ) {
            Text(appString(language, TextKey.Auth), style = MaterialTheme.typography.headlineMedium)
        }

        AnimatedVisibility(
            visible = state.visible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                component.providers.forEach { provider ->
                    ProviderButton(
                        provider = provider,
                        loading = state.loadingProvider == provider,
                        enabled = state.loadingProvider == null,
                        onClick = { intent(AuthContract.Intent.Authenticate(provider)) },
                    )
                }
                state.error?.let {
                    Text(
                        text = appString(language, TextKey.NotConfigured),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("auth_error"),
                    )
                }
            }
        }
    }
}

@Composable
private fun ProviderButton(
    provider: SocialProvider,
    loading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val label = "Continue with ${provider.name}"
    val modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 48.dp)
        .testTag("auth_${provider.name.lowercase()}")

    when (provider) {
        SocialProvider.Apple -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
        ) { ButtonContent(label, loading) }

        SocialProvider.Google -> OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) { ButtonContent(label, loading) }

        SocialProvider.Meta -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = HealthierTokens.meta),
        ) { ButtonContent(label, loading) }

        SocialProvider.Telegram -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(containerColor = HealthierTokens.telegram),
        ) { ButtonContent(label, loading) }
    }
}

@Composable
private fun ButtonContent(label: String, loading: Boolean) {
    if (loading) {
        CircularProgressIndicator(strokeWidth = 2.dp)
    } else {
        Text(label)
    }
}
