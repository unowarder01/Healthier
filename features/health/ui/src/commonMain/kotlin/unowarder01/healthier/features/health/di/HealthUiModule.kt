package unowarder01.healthier.features.health.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.health.ui.HealthComponent
import unowarder01.healthier.features.health.ui.HealthNavigator
import unowarder01.healthier.features.health.ui.HealthViewModel

val healthUiModule = module {
    factoryOf(::HealthViewModel)
    factory { (context: ComponentContext) ->
        HealthComponent(
            context = context,
            viewModel = get(),
            navigator = get<HealthNavigator>(),
        )
    }
}
