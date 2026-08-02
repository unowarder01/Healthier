package unowarder01.healthier.navigation.screens.main.root_navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import unowarder01.healthier.navigation.bottom_navigation.MainBottomNavigation

@Composable
fun MainScreensContent(navigator: MainScreensNavigator) {
    val pages by navigator.pages.subscribeAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ChildPages(
            pages = navigator.pages,
            onPageSelected = navigator::selectPage,
            modifier = Modifier.fillMaxSize(),
            scrollAnimation = PagesScrollAnimation.Default,
            pageContent = { _, child -> navigator.getContentByChild(child) }
        )
        MainBottomNavigation(
            selectedItemIndex = pages.selectedIndex,
            onItemSelected = navigator::selectPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        )
    }
}
