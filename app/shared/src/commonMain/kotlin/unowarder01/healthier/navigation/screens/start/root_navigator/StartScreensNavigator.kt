package unowarder01.healthier.navigation.screens.start.root_navigator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface StartScreensNavigator {
    val router: Value<ChildStack<StartScreensConfig, StartScreensChild>>

    @Composable
    fun getContentByChild(child: StartScreensChild)
}

