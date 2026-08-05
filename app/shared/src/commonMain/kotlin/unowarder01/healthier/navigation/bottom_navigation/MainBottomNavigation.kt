package unowarder01.healthier.navigation.bottom_navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import unowarder01.healthier.core.designsystem.components.container.ContainerWithIndicator
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_tab_calendar_selected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_calendar_unselected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_health_selected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_health_unselected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_history_selected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_history_unselected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_map_selected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_map_unselected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_profile_selected
import unowarder01.healthier.designsystem.generated.resources.ic_tab_profile_unselected

@Composable
fun MainBottomNavigation(
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    indicatorPadding: Dp = 4.dp,
    scale: Float = 1f,
) {
    ContainerWithIndicator(
        items = mainBottomNavigationItems,
        selectedItemIndex = selectedItemIndex,
        withShadow = true,
        containerColor = colorScheme.surfaceContainerLow,
        indicatorColor = colorScheme.primaryContainer,
        borderColor = colorScheme.outlineVariant,
        indicatorPadding = indicatorPadding,
        onItemClick = { index ->
            onItemSelected(index)
        },
        itemContent = { index, item ->
            MainBottomNavigationItem(
                item = item,
                selected = index == selectedItemIndex,
                onClick = { onItemSelected(index) }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .height(64.dp)
            .scale(scale)
    )
}

@Composable
private fun MainBottomNavigationItem(
    item: MainBottomNavigationItemModel,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        animationSpec = tween(),
        targetValue = if (selected) colorScheme.onPrimaryContainer else colorScheme.onSurfaceVariant
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        AppImage(
            image = if (selected) item.selectedIcon else item.unselectedIcon,
            color = color,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Immutable
data class MainBottomNavigationItemModel(
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource
)

private val mainBottomNavigationItems = listOf(
    MainBottomNavigationItemModel(
        selectedIcon = Res.drawable.ic_tab_health_selected,
        unselectedIcon = Res.drawable.ic_tab_health_unselected
    ),
    MainBottomNavigationItemModel(
        selectedIcon = Res.drawable.ic_tab_map_selected,
        unselectedIcon = Res.drawable.ic_tab_map_unselected
    ),
    MainBottomNavigationItemModel(
        selectedIcon = Res.drawable.ic_tab_calendar_selected,
        unselectedIcon = Res.drawable.ic_tab_calendar_unselected
    ),
    MainBottomNavigationItemModel(
        selectedIcon = Res.drawable.ic_tab_history_selected,
        unselectedIcon = Res.drawable.ic_tab_history_unselected
    ),
    MainBottomNavigationItemModel(
        selectedIcon = Res.drawable.ic_tab_profile_selected,
        unselectedIcon = Res.drawable.ic_tab_profile_unselected
    )
)
