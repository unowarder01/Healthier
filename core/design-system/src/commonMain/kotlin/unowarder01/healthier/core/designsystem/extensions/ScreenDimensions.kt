package unowarder01.healthier.core.designsystem.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
fun getScreenWidth() = with(LocalDensity.current) {
    LocalWindowInfo.current
        .containerSize
        .width
        .dp / density
}

@Composable
fun getScreenHeight() = with(LocalDensity.current) {
    LocalWindowInfo.current
        .containerSize
        .height
        .dp / density
}