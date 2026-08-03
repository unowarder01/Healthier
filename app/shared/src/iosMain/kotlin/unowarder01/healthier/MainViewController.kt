package unowarder01.healthier

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import platform.UIKit.UIViewController
import unowarder01.healthier.di.appModules
import unowarder01.healthier.extensions.initKoin

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
fun MainViewController(): UIViewController {
    initKoin(appModules)
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    val root = createRootComponent(
        DefaultComponentContext(lifecycle)
    )
    return ComposeUIViewController { App(root) }
}
