package unowarder01.healthier

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import unowarder01.healthier.core.designsystem.theme.HealthierTheme

@Composable
fun App(root: RootComponent) {
    HealthierTheme(
        systemDark = isSystemInDarkTheme()
    ) {
        AppContent(root)
    }
}

@Composable
private fun AppContent(root: RootComponent) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ScreensContent(root)
    }
}

@Composable
private fun ScreensContent(root: RootComponent) {
    val stack by root.navigator.router.subscribeAsState()
    Children(
        stack = stack,
        modifier = Modifier.fillMaxSize(),
        animation = stackAnimation(slide()),
        content = { child ->
            root.navigator.getContentByChild(child.instance)
        }
    )
}
