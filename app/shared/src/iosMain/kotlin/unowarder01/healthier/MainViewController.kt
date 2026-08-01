package unowarder01.healthier

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
fun MainViewController(): platform.UIKit.UIViewController {
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    val root = createRootComponent(
        DefaultComponentContext(lifecycle)
    )
    return ComposeUIViewController { App(root) }
}
