package unowarder01.healthier.core.designsystem.components.text_field

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.core.designsystem.components.text_field.custom_text_field.CustomTextField

private const val BORDER_ANIMATION_DURATION_MILLIS = 150

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: DrawableResource? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLength: Int? = null,
    onFocusChanged: (Boolean) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> colorScheme.outlineVariant
            isError -> colorScheme.error
            isFocused -> colorScheme.primary
            else -> colorScheme.outline
        },
        animationSpec = tween(BORDER_ANIMATION_DURATION_MILLIS),
    )
    val iconColor by animateColorAsState(
        targetValue = when {
            isError -> colorScheme.error
            isFocused -> colorScheme.primary
            else -> colorScheme.onSurfaceVariant
        },
        animationSpec = tween(BORDER_ANIMATION_DURATION_MILLIS),
    )
    CustomTextField(
        value = value,
        onValueChange = { newValue ->
            val normalizedValue = newValue.replace("\n", " ")
            onValueChange(maxLength?.let(normalizedValue::take) ?: normalizedValue)
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = typography.bodyMedium,
        label = label?.let { text ->
            {
                Text(
                    text = text,
                    style = typography.bodySmall
                )
            }
        },
        placeholder = placeholder?.let { text ->
            {
                Text(
                    text = text,
                    style = typography.bodyMedium
                )
            }
        },
        leadingIcon = leadingIcon?.let { icon ->
            {
                AppImage(
                    image = icon,
                    color = iconColor,
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .size(12.dp)
                )
            }
        },
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = shapes.large,
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            disabledTextColor = colorScheme.onSurfaceVariant,
            errorTextColor = colorScheme.onSurface,
            focusedContainerColor = colorScheme.surfaceContainer,
            unfocusedContainerColor = colorScheme.surfaceContainer,
            disabledContainerColor = colorScheme.surfaceContainerHigh,
            errorContainerColor = colorScheme.surfaceContainer,
            cursorColor = colorScheme.primary,
            errorCursorColor = colorScheme.error,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedLabelColor = colorScheme.primary,
            unfocusedLabelColor = colorScheme.onSurfaceVariant,
            disabledLabelColor = colorScheme.onSurfaceVariant,
            errorLabelColor = colorScheme.error,
            focusedPlaceholderColor = colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = colorScheme.onSurfaceVariant,
            errorPlaceholderColor = colorScheme.onSurfaceVariant,
            focusedLeadingIconColor = colorScheme.primary,
            unfocusedLeadingIconColor = colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = colorScheme.onSurfaceVariant,
            errorLeadingIconColor = colorScheme.error,
            focusedTrailingIconColor = colorScheme.primary,
            unfocusedTrailingIconColor = colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = colorScheme.onSurfaceVariant,
            errorTrailingIconColor = colorScheme.error,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = borderColor,
                shape = shapes.large,
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                onFocusChanged(focusState.isFocused)
            }
    )
}
