package unowarder01.healthier

import com.arkivanov.decompose.ComponentContext
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import unowarder01.healthier.di.appModules
import unowarder01.healthier.navigation.screens.start.root_navigator.StartScreensNavigator

class AppGraph {
    private val application = koinApplication {
        modules(appModules)
    }

    val koin: Koin get() = application.koin
}

class RootComponent(
    context: ComponentContext,
    graph: AppGraph
) : ComponentContext by context {
    val navigator = graph.koin.get<StartScreensNavigator> { parametersOf(context) }
}

fun createRootComponent(
    componentContext: ComponentContext
): RootComponent = RootComponent(
    context = componentContext,
    graph = AppGraph()
)
