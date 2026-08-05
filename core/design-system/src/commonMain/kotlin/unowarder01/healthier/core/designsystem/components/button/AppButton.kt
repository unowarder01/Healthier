package unowarder01.healthier.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import unowarder01.healthier.core.designsystem.components.button.AppButtonStyle.Destructive
import unowarder01.healthier.core.designsystem.components.button.AppButtonStyle.Inverse
import unowarder01.healthier.core.designsystem.components.button.AppButtonStyle.Outlined
import unowarder01.healthier.core.designsystem.components.button.AppButtonStyle.Primary
import unowarder01.healthier.core.designsystem.components.button.AppButtonStyle.Text
import unowarder01.healthier.core.designsystem.components.button.AppButtonStyle.Tonal

enum class AppButtonStyle {
    Primary,
    Tonal,
    Outlined,
    Destructive,
    Text,
    Inverse
}

@Composable
fun AppButton(
    text: String,
    style: TextStyle? = null,
    shape: Shape = shapes.large,
    buttonStyle: AppButtonStyle,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val content = @Composable {
        Text(
            text = text,
            style = style ?: typography.labelLarge
        )
    }
    when (buttonStyle) {
        Primary -> Button(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ),
            content = { content() }
        )
        Tonal -> FilledTonalButton(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = colorScheme.secondaryContainer,
                contentColor = colorScheme.onSecondaryContainer
            ),
            content = { content() }
        )
        Outlined -> OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            border = BorderStroke(1.dp, colorScheme.outline),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorScheme.onSurface
            ),
            content = { content() }
        )
        Destructive -> Button(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.error,
                contentColor = colorScheme.onError
            ),
            content = { content() }
        )
        Text -> TextButton(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorScheme.primary
            ),
            content = { content() }
        )
        Inverse -> Button(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.inverseSurface,
                contentColor = colorScheme.inverseOnSurface
            ),
            content = { content() }
        )
    }
}
