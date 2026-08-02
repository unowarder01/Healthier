package unowarder01.healthier.core.designsystem.components.image

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_app_logo

@Composable
fun AppLogo(
    shape: Shape = shapes.extraLarge,
    modifier: Modifier
) {
    AppImage(
        image = Res.drawable.ic_app_logo,
        color = Color(0xFFF7F7FA),
        modifier = modifier
            .clip(shape)
            .background(
                color = Color(0xFF0E0E12),
                shape = shape
            )
    )
}
