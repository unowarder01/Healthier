package unowarder01.healthier.core.designsystem.components.container

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import unowarder01.healthier.core.designsystem.extensions.outerShadow

@Composable
fun <T> ContainerWithIndicator(
    modifier: Modifier,
    items: List<T>,
    selectedItemIndex: Int,
    onItemClick: (itemIndex: Int) -> Unit,
    itemContent: @Composable BoxScope.(index: Int, item: T) -> Unit,
    withShadow: Boolean = false,
    containerBackgroundColor: Color = colorScheme.onPrimary,
    indicatorPadding: Dp = 0.dp
) {
    val density = LocalDensity.current
    var parentWidth by remember { mutableIntStateOf(0) }
    val itemWidth = when {
        parentWidth > 0 -> with(density) { parentWidth.toDp() } / items.size
        else -> 0.dp
    }
    val animatedOffset by animateDpAsState(
        animationSpec = tween(),
        targetValue = itemWidth * selectedItemIndex
    )
    Box(
        modifier = modifier
            .run {
                if (!withShadow) this else outerShadow(shape = shapes.extraLarge)
            }
            .clip(shapes.extraLarge)
            .background(
                color = containerBackgroundColor,
                shape = shapes.extraLarge
            )
    ) {
        Indicator(
            itemWidth = itemWidth,
            animatedOffset = animatedOffset,
            padding = indicatorPadding
        )
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .onGloballyPositioned { coordinates ->
                    parentWidth = coordinates.size.width
                }
        ) {
            items.forEachIndexed { index, item ->
                Item(
                    itemContent = { itemContent(index, item) },
                    onClick = { onItemClick(index) }
                )
            }
        }
    }
}

@Composable
private fun RowScope.Item(
    itemContent: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(shapes.extraLarge)
            .clickable { onClick() }
    ) {
        itemContent()
    }
}

@Composable
private fun Indicator(
    itemWidth: Dp,
    animatedOffset: Dp,
    padding: Dp
) {
    Box(
        modifier = Modifier
            .offset(x = animatedOffset)
            .width(itemWidth)
            .fillMaxHeight()
            .padding(padding)
            .clip(shapes.extraLarge)
            .background(
                color = colorScheme.secondaryContainer,
                shape = shapes.extraLarge
            )
    )
}
