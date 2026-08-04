package unowarder01.healthier.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import platform.UIKit.UIApplication
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIWindow

@Composable
internal actual fun SystemBarsAppearance(
    darkTheme: Boolean,
    restoreDarkTheme: Boolean?
) {
    val windows = UIApplication.sharedApplication.windows.filterIsInstance<UIWindow>()
    val originalStyles = remember(windows) {
        windows.associateWith { it.overrideUserInterfaceStyle }
    }
    val shouldOverrideSystemStyle = restoreDarkTheme != null

    DisposableEffect(windows, darkTheme, shouldOverrideSystemStyle) {
        if (shouldOverrideSystemStyle) {
            windows.applyInterfaceStyle(darkTheme)
        }

        onDispose {
            if (shouldOverrideSystemStyle) {
                originalStyles.forEach { (window, style) ->
                    window.overrideUserInterfaceStyle = style
                }
            }
        }
    }
}

private fun List<UIWindow>.applyInterfaceStyle(darkTheme: Boolean) {
    val style = if (darkTheme) {
        UIUserInterfaceStyle.UIUserInterfaceStyleDark
    } else {
        UIUserInterfaceStyle.UIUserInterfaceStyleLight
    }
    forEach { window -> window.overrideUserInterfaceStyle = style }
}
