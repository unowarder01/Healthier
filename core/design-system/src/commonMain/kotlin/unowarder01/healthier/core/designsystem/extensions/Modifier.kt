package unowarder01.healthier.core.designsystem.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickableWithoutShadow(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    callback: () -> Unit
) = clickable(
    enabled = enabled,
    indication = null,
    interactionSource = interactionSource,
    onClick = callback
)