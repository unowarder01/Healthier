package unowarder01.healthier.core.designsystem.components.image

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import unowarder01.healthier.core.designsystem.theme.HealthierBrandBlue
import unowarder01.healthier.core.designsystem.theme.HealthierOnBrandBlue
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_app_logo

@Composable
fun AppLogo(
    shape: Shape = shapes.extraLarge,
    modifier: Modifier
) {
    AppImage(
        image = Res.drawable.ic_app_logo,
        color = HealthierOnBrandBlue,
        modifier = modifier
            .clip(shape)
            .background(
                color = HealthierBrandBlue,
                shape = shape
            )
    )
}
