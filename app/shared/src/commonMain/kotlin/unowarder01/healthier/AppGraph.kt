package unowarder01.healthier

import com.arkivanov.decompose.ComponentContext
import onboardingFeatureModule
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import unowarder01.healthier.features.auth.di.authFeatureModule
import unowarder01.healthier.features.splash.di.splashFeatureModule
import unowarder01.healthier.navigation.screens.AppScreensNavigator
import unowarder01.healthier.navigation.appNavigationModule

class AppGraph {
    private val application = koinApplication {
        modules(
            splashFeatureModule,
            onboardingFeatureModule,
            authFeatureModule,
            appNavigationModule
        )
    }

    val koin: Koin
        get() = application.koin
}

class RootComponent(
    context: ComponentContext,
    graph: AppGraph
) : ComponentContext by context {
    val navigator: AppScreensNavigator = graph.koin.get {
        parametersOf(context)
    }
}

fun createRootComponent(
    componentContext: ComponentContext
): RootComponent = RootComponent(
    context = componentContext,
    graph = AppGraph()
)
