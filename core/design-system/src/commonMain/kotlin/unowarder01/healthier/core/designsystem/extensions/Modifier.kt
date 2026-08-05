package unowarder01.healthier.core.designsystem.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

/**
 * CLICKS
 */
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

@Composable
fun Modifier.clearFocusOnTap(): Modifier {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    return pointerInput(focusManager, keyboardController) {
        detectTapGestures {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }
}

/**
 * SHADOW
 */
@Composable
fun Modifier.outerShadow(
    shape: Shape,
    color: Color = colorScheme.scrim.copy(alpha = 0.1f),
    radius: Dp = 8.dp,
    spread: Dp = 8.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
): Modifier = this then dropShadow(
    shape = shape,
    shadow = Shadow(
        radius = radius,
        spread = spread,
        color = color,
        offset = DpOffset(x = offsetX, y = offsetY)
    )
)

/**
 * SIZE
 */
@Composable
fun Modifier.calculateHeightAndSetTo(heightHolder: MutableState<Dp>): Modifier {
    val density = LocalDensity.current
    return onSizeChanged { size ->
        heightHolder.value = size.height.dp / density.density
    }
}

@Composable
fun Modifier.calculateWidthAndSetTo(sizeHolder: MutableState<Dp>): Modifier {
    val density = LocalDensity.current
    return onSizeChanged { size ->
        sizeHolder.value = size.width.dp / density.density
    }
}
