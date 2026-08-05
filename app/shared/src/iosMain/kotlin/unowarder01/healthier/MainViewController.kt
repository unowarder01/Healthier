package unowarder01.healthier

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import unowarder01.healthier.di.appModules
import unowarder01.healthier.extensions.initKoin
import unowarder01.healthier.features.map.ui.installIosGoogleMapViewFactory

interface IosGoogleMapViewFactory {
    fun createView(
        latitude: Double,
        longitude: Double,
        zoom: Float,
    ): UIView
}

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
fun MainViewController(
    googleMapViewFactory: IosGoogleMapViewFactory,
): UIViewController {
    installIosGoogleMapViewFactory(googleMapViewFactory::createView)
    initKoin(appModules)
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    val root = createRootComponent(
        DefaultComponentContext(lifecycle)
    )
    return ComposeUIViewController { App(root) }
}
