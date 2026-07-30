package unowarder01.healthier

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import unowarder01.healthier.core.designsystem.HealthierTheme

@Composable
fun App(root: RootComponent) {
    val theme by root.settings.theme.collectAsState()
    val stack by root.navigator.router.subscribeAsState()
    val dialogSlot by root.dialogs.router.subscribeAsState()

    HealthierTheme(
        theme = theme,
        systemDark = isSystemInDarkTheme()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Children(
                stack = stack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(slide())
            ) { child ->
                root.navigator.getContentByChild(child.instance)
            }
            dialogSlot.child?.instance?.let { child ->
                root.dialogs.getContentByChild(child)
            }
        }
    }
}
