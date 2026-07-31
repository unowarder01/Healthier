package unowarder01.healthier.core.designsystem.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_app_logo

private const val CONTENT_DESCRIPTION = "AppImage"
private const val REMOTE_CONTENT_DESCRIPTION = "AppImage"

@Composable
fun AppImage(
    image: DrawableResource,
    modifier: Modifier = Modifier,
    color: Color? = null,
    scale: ContentScale = ContentScale.Fit,
    contentDescription: String = CONTENT_DESCRIPTION
) {
    Image(
        painter = painterResource(image),
        contentDescription = contentDescription,
        colorFilter = color?.let { ColorFilter.tint(it) },
        contentScale = scale,
        modifier = modifier
    )
}

@Composable
fun AppImage(
    image: ImageVector,
    modifier: Modifier = Modifier,
    color: Color? = null,
    scale: ContentScale = ContentScale.Fit
) {
    Image(
        imageVector = image,
        contentDescription = CONTENT_DESCRIPTION,
        colorFilter = color?.let { ColorFilter.tint(it) },
        contentScale = scale,
        modifier = modifier
    )
}

@Composable
fun AppImage(
    url: String,
    modifier: Modifier = Modifier,
    scale: ContentScale = ContentScale.Fit,
    loadingPlaceholderSettings: AppImageSettings? = null,
    errorPlaceholderSettings: AppImageSettings? = null
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = REMOTE_CONTENT_DESCRIPTION,
        modifier = modifier,
        loading = {
            loadingPlaceholderSettings?.let { settings ->
                PlaceholderImage(settings)
            }
        },
        error = {
            errorPlaceholderSettings?.let { settings ->
                PlaceholderImage(settings)
            }
        },
        success = { SubcomposeAsyncImageContent() },
        contentScale = scale
    )
}

@Composable
private fun PlaceholderImage(
    placeholderSettings: AppImageSettings,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AppImage(
            image = placeholderSettings.image,
            modifier = placeholderSettings.modifier,
            scale = placeholderSettings.scale,
            color = placeholderSettings.color
        )
    }
}

@Stable
data class AppImageSettings(
    val image: DrawableResource = Res.drawable.ic_app_logo, // TODO: Change to placeholder
    val modifier: Modifier = Modifier,
    val color: Color? = null,
    val scale: ContentScale = ContentScale.Fit
)