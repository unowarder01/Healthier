package unowarder01.healthier.navigation.screens.main.root_navigator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface MainScreensNavigator {
    val router: Value<ChildStack<MainScreensConfig, MainScreensChild>>

    @Composable
    fun getContentByChild(child: MainScreensChild)
}