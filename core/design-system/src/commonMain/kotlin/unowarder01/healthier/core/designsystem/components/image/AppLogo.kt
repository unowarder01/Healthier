package unowarder01.healthier.core.designsystem.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_app_logo

@Composable
fun AppLogo(
    modifier: Modifier
) {
    AppImage(
        image = Res.drawable.ic_app_logo,
        modifier = modifier
    )
}
