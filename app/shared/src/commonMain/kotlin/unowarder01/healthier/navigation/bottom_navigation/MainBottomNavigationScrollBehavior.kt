package unowarder01.healthier.navigation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val ExpandedScale = 1f
private const val DefaultCollapsedScale = 0.95f
private val DefaultCollapseDistance = 64.dp

@Stable
class MainBottomNavigationScrollBehavior internal constructor(
    private val collapsedScale: Float,
    private val collapseDistancePx: Float
) {
    init {
        require(collapsedScale in 0f..ExpandedScale)
        require(collapseDistancePx > 0f)
    }

    var scale by mutableFloatStateOf(ExpandedScale)
        private set

    val nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val scaleDelta = available.y / collapseDistancePx * (ExpandedScale - collapsedScale)
            scale = (scale + scaleDelta).coerceIn(
                minimumValue = collapsedScale,
                maximumValue = ExpandedScale
            )
            return Offset.Zero
        }
    }
}

@Composable
fun rememberMainBottomNavigationScrollBehavior(
    collapsedScale: Float = DefaultCollapsedScale,
    collapseDistance: Dp = DefaultCollapseDistance
): MainBottomNavigationScrollBehavior {
    val collapseDistancePx = with(LocalDensity.current) {
        collapseDistance.toPx()
    }
    return remember(collapsedScale, collapseDistancePx) {
        MainBottomNavigationScrollBehavior(
            collapsedScale = collapsedScale,
            collapseDistancePx = collapseDistancePx
        )
    }
}
