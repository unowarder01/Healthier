package unowarder01.healthier.core.designsystem.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
internal actual fun SystemBarsAppearance(
    darkTheme: Boolean,
    restoreDarkTheme: Boolean?
) {
    val view = LocalView.current
    if (view.isInEditMode) return

    val window = view.context.findActivity()?.window ?: return
    val controller = remember(window, view) {
        WindowCompat.getInsetsController(window, view)
    }
    val originalLightStatusBars = remember(controller) {
        controller.isAppearanceLightStatusBars
    }
    val originalLightNavigationBars = remember(controller) {
        controller.isAppearanceLightNavigationBars
    }

    DisposableEffect(controller, darkTheme, restoreDarkTheme) {
        controller.isAppearanceLightStatusBars = !darkTheme
        controller.isAppearanceLightNavigationBars = !darkTheme

        onDispose {
            if (restoreDarkTheme != null) {
                controller.isAppearanceLightStatusBars = !restoreDarkTheme
                controller.isAppearanceLightNavigationBars = !restoreDarkTheme
            } else {
                controller.isAppearanceLightStatusBars = originalLightStatusBars
                controller.isAppearanceLightNavigationBars = originalLightNavigationBars
            }
        }
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
