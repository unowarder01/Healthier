package unowarder01.healthier.features.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap as AndroidGoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
internal actual fun GoogleMap(
    initialCameraPosition: MapCameraPosition,
    modifier: Modifier,
) {
    val cameraPosition = remember(initialCameraPosition) {
        CameraPosition.fromLatLngZoom(
            LatLng(
                initialCameraPosition.latitude,
                initialCameraPosition.longitude,
            ),
            initialCameraPosition.zoom,
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = cameraPosition
    }

    AndroidGoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
    )
}
