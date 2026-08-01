package unowarder01.healthier

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    val root = createRootComponent(
        DefaultComponentContext(lifecycle)
    )
    ComposeViewport {
        App(root)
    }
}
