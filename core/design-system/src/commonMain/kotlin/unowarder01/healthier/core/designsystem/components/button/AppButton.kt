package unowarder01.healthier.core.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import unowarder01.healthier.core.designsystem.components.button.AppButtonState.BlackWithWhiteText
import unowarder01.healthier.core.designsystem.components.button.AppButtonState.RedWithWhiteText
import unowarder01.healthier.core.designsystem.components.button.AppButtonState.TransparentWithPurpleText
import unowarder01.healthier.core.designsystem.components.button.AppButtonState.WhiteWithBlackText
import unowarder01.healthier.core.designsystem.extensions.clickableWithoutShadow

enum class AppButtonState {
    BlackWithWhiteText,
    WhiteWithBlackText,
    RedWithWhiteText,
    TransparentWithPurpleText,
}

@Composable
fun AppButton(
    text: String,
    style: TextStyle? = null,
    state: AppButtonState,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val backgroundColor = when (state) {
        BlackWithWhiteText -> colorScheme.primary
        WhiteWithBlackText -> colorScheme.surface
        RedWithWhiteText -> colorScheme.error
        TransparentWithPurpleText -> Color.Transparent
    }
    val borderColor = when (state) {
        WhiteWithBlackText -> colorScheme.outline
        else -> Color.Transparent
    }
    val textColor = when (state) {
        BlackWithWhiteText -> colorScheme.onPrimary
        WhiteWithBlackText -> colorScheme.onSurface
        RedWithWhiteText -> colorScheme.onError
        TransparentWithPurpleText -> colorScheme.secondary
    }
    val clickModifier = when (state) {
        TransparentWithPurpleText -> Modifier.clickableWithoutShadow { onClick() }
        else -> Modifier.clickable { onClick() }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .widthIn(min = 108.dp)
            .height(48.dp)
            .clip(shapes.large)
            .background(
                color = backgroundColor,
                shape = shapes.large
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.large
            )
            .then(clickModifier)
    ) {
        Text(
            text = text,
            color = textColor,
            style = style ?: typography.labelLarge
        )
    }
}
