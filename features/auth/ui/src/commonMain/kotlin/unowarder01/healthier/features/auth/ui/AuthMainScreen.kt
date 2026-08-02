package unowarder01.healthier.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.core.designsystem.components.image.AppLogo
import unowarder01.healthier.core.designsystem.components.text.ClickableTextWithTags
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.authorization
import unowarder01.healthier.designsystem.generated.resources.continue_with_apple
import unowarder01.healthier.designsystem.generated.resources.continue_with_google
import unowarder01.healthier.designsystem.generated.resources.continue_with_meta
import unowarder01.healthier.designsystem.generated.resources.continue_with_telegram
import unowarder01.healthier.designsystem.generated.resources.ic_apple
import unowarder01.healthier.designsystem.generated.resources.ic_google
import unowarder01.healthier.designsystem.generated.resources.ic_meta
import unowarder01.healthier.designsystem.generated.resources.ic_telegram
import unowarder01.healthier.designsystem.generated.resources.terms_and_privacy_agreement
import unowarder01.healthier.features.auth.ui.AuthContract.Listener
import unowarder01.healthier.features.auth.ui.AuthContract.State
import unowarder01.healthier.features.auth.ui.ClickableTextTags.PRIVACY_TAG
import unowarder01.healthier.features.auth.ui.ClickableTextTags.TERMS_TAG

@Composable
fun AuthMainScreen(
    state: State,
    listener: Listener
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .systemBarsPadding()
    ) {
        AppLogo()
        Spacer(modifier = Modifier.weight(1f))
        Title()
        Spacer(modifier = Modifier.weight(1f))
        AuthButtons()
        AgreementText()
    }
}

/**
 * LOGO
 */
@Composable
private fun AppLogo() {
    AppLogo(
        modifier = Modifier
            .padding(top = 16.dp)
            .size(108.dp)
    )
}

/**
 * TITLE
 */
@Composable
private fun Title() {
    Text(
        text = stringResource(Res.string.authorization),
        color = colorScheme.onBackground,
        style = typography.headlineLarge,
    )
}

/**
 * BUTTONS
 */
@Composable
private fun AuthButtons() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        listOf(
            Res.string.continue_with_apple to Res.drawable.ic_apple,
            Res.string.continue_with_google to Res.drawable.ic_google,
            Res.string.continue_with_meta to Res.drawable.ic_meta,
            Res.string.continue_with_telegram to Res.drawable.ic_telegram,
        ).forEach { (text, icon) ->
            AuthButton(text = text, icon = icon)
        }
    }
}

@Composable
private fun AuthButton(
    text: StringResource,
    icon: DrawableResource
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
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
    ) {
        AppImage(
            image = icon,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
                .size(28.dp)
        )
        Text(
            text = stringResource(text),
            color = colorScheme.onSurface,
            style = typography.titleMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

/**
 * AGREEMENT TEXT
 */
@Composable
private fun AgreementText() {
    ClickableTextWithTags(
        fullText = stringResource(Res.string.terms_and_privacy_agreement),
        tagHandlers = mapOf(
            TERMS_TAG to {},
            PRIVACY_TAG to {}
        ),
        linkStyle = SpanStyle(color = colorScheme.onSurface),
        textStyle = typography.labelSmall,
        textColor = colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
    )
}

private object ClickableTextTags {
    const val TERMS_TAG = "terms"
    const val PRIVACY_TAG = "privacy"
}
