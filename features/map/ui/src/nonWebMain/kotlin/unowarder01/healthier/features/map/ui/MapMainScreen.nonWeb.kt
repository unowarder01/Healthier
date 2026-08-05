package unowarder01.healthier.features.map.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val TbilisiCameraPosition = MapCameraPosition(
    latitude = 41.7151,
    longitude = 44.8271,
    zoom = 12f,
)

@Composable
actual fun MapMainScreen() {
    GoogleMap(
        initialCameraPosition = TbilisiCameraPosition,
        modifier = Modifier.fillMaxSize(),
    )
}

internal data class MapCameraPosition(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float,
)

@Composable
internal expect fun GoogleMap(
    initialCameraPosition: MapCameraPosition,
    modifier: Modifier = Modifier,
)
