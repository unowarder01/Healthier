package unowarder01.healthier.navigation.screens.main.root_navigator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value

interface MainScreensNavigator {
    val pages: Value<ChildPages<MainScreensConfig, MainScreensChild>>

    fun selectPage(index: Int)

    @Composable
    fun getContentByChild(child: MainScreensChild)
}
