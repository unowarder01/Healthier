package unowarder01.healthier.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.features.home.domain.HomeTab

@Composable
fun HomeMainScreen(
    component: HomeComponent,
    language: AppLanguage,
    health: @Composable () -> Unit,
    map: @Composable () -> Unit,
    profile: @Composable () -> Unit,
) = with(component.store) {
    val state by subscribe()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(Modifier.testTag("home_bottom_navigation")) {
                HomeTab.entries.forEach { tab ->
                    val text = when (tab) {
                        HomeTab.Health -> appString(language, TextKey.Health)
                        HomeTab.Map -> appString(language, TextKey.Map)
                        HomeTab.Profile -> appString(language, TextKey.Profile)
                    }
                    NavigationBarItem(
                        selected = state.selectedTab == tab,
                        onClick = { component.selectTab(tab) },
                        icon = { Text(tabIcon(tab)) },
                        label = { Text(text) },
                        modifier = Modifier.testTag("tab_${tab.name.lowercase()}"),
                    )
                }
            }
        },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (state.selectedTab) {
                HomeTab.Health -> health()
                HomeTab.Map -> map()
                HomeTab.Profile -> profile()
            }
        }
    }
}

private fun tabIcon(tab: HomeTab): String = when (tab) {
    HomeTab.Health -> "♥"
    HomeTab.Map -> "⌖"
    HomeTab.Profile -> "●"
}
