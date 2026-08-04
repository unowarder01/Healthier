package unowarder01.healthier.navigation.screens.main.root_navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation.Default
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import unowarder01.healthier.navigation.bottom_navigation.MainBottomNavigation

@Composable
fun MainScreensContent(navigator: MainScreensNavigator) {
    val pages by navigator.pages.subscribeAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainBottomNavigation(
                selectedItemIndex = pages.selectedIndex,
                onItemSelected = navigator::selectPage,
            )
        }
    ) {
        ChildPages(
            pages = navigator.pages,
            onPageSelected = navigator::selectPage,
            scrollAnimation = Default,
            pager = { modifier, state, key, pageContent ->
                HorizontalPager(
                    modifier = modifier,
                    key = key,
                    state = state,
                    userScrollEnabled = false,
                    pageContent = pageContent
                )
            },
            pageContent = { _, child ->
                navigator.getContentByChild(child)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
