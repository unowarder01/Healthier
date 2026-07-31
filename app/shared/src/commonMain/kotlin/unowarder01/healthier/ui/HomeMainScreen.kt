package unowarder01.healthier.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.strings.TextKey
import unowarder01.healthier.core.designsystem.strings.appString

@Composable
fun HomeMainScreen(
    language: AppLanguage,
    health: @Composable () -> Unit,
    map: @Composable () -> Unit,
    profile: @Composable () -> Unit
) {
    var selectedTab by remember { mutableStateOf(HomeTab.Health) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("home_bottom_navigation")
            ) {
                HomeTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { TabIcon(tab) },
                        label = { Text(tab.label(language)) },
                        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                HomeTab.Health -> health()
                HomeTab.Map -> map()
                HomeTab.Profile -> profile()
            }
        }
    }
}

@Composable
private fun TabIcon(tab: HomeTab) {
    val imageVector = when (tab) {
        HomeTab.Health -> Icons.Default.FavoriteBorder
        HomeTab.Map -> Icons.Default.Map
        HomeTab.Profile -> Icons.Default.AccountCircle
    }

    Icon(
        imageVector = imageVector,
        contentDescription = null
    )
}

@Composable
private fun HomeTab.label(language: AppLanguage): String = when (this) {
    HomeTab.Health -> appString(language, TextKey.Health)
    HomeTab.Map -> appString(language, TextKey.Map)
    HomeTab.Profile -> appString(language, TextKey.Profile)
}

private enum class HomeTab {
    Health,
    Map,
    Profile
}
