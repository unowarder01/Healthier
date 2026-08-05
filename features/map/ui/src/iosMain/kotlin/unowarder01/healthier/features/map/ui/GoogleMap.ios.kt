package unowarder01.healthier.features.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.UIView

private var googleMapViewFactory: ((Double, Double, Float) -> UIView)? = null

fun installIosGoogleMapViewFactory(
    factory: (latitude: Double, longitude: Double, zoom: Float) -> UIView,
) {
    googleMapViewFactory = factory
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun GoogleMap(
    initialCameraPosition: MapCameraPosition,
    modifier: Modifier,
) {
    UIKitView(
        factory = {
            checkNotNull(googleMapViewFactory) {
                "The iOS Google Maps view factory must be installed before composition starts"
            }(
                initialCameraPosition.latitude,
                initialCameraPosition.longitude,
                initialCameraPosition.zoom,
            )
        },
        modifier = modifier,
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
            isNativeAccessibilityEnabled = true,
        ),
    )
}
