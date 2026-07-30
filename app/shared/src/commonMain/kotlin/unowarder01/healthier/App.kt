package unowarder01.healthier

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import unowarder01.healthier.core.designsystem.HealthierTheme
import unowarder01.healthier.features.auth.ui.AuthMainScreen
import unowarder01.healthier.features.city.ui.ChooseCityMainScreen
import unowarder01.healthier.features.health.ui.HealthMainScreen
import unowarder01.healthier.features.home.ui.HomeMainScreen
import unowarder01.healthier.features.map.ui.MapMainScreen
import unowarder01.healthier.features.profile.ui.ProfileMainScreen
import unowarder01.healthier.features.splash.ui.SplashMainScreen

@Composable
fun App(root: RootComponent) {
    val language by root.settings.language.collectAsState()
    val theme by root.settings.theme.collectAsState()
    val stack by root.stack.subscribeAsState()

    HealthierTheme(theme = theme, systemDark = isSystemInDarkTheme()) {
        Children(
            stack = stack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(slide()),
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Splash -> SplashMainScreen(instance.component)
                is RootComponent.Child.Auth -> AuthMainScreen(instance.component, language)
                is RootComponent.Child.City -> ChooseCityMainScreen(instance.component, language)
                is RootComponent.Child.Home -> HomeMainScreen(
                    component = instance.home,
                    language = language,
                    health = { HealthMainScreen(instance.health, language) },
                    map = { MapMainScreen(instance.map, language) },
                    profile = { ProfileMainScreen(instance.profile) },
                )
            }
        }
    }
}
