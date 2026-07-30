package unowarder01.healthier

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import kotlin.native.Platform
import platform.Foundation.NSBundle
import unowarder01.healthier.core.database.createIosClinicCache

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
fun MainViewController(): platform.UIKit.UIViewController {
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    val root = createRootComponent(
        DefaultComponentContext(lifecycle),
        AppRuntimeConfig(
            isDebug = Platform.isDebugBinary,
            apiBaseUrl = NSBundle.mainBundle
                .objectForInfoDictionaryKey("HEALTHIER_API_BASE_URL") as? String ?: ""
        ),
        createIosClinicCache()
    )
    return ComposeUIViewController { App(root) }
}
