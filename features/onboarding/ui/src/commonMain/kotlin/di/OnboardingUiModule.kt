package di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.OnboardingComponent
import ui.OnboardingNavigator
import ui.OnboardingViewModel

val onboardingUiModule = module {
    factoryOf(::OnboardingViewModel)
    factory { (context: ComponentContext) ->
        OnboardingComponent(
            context = context,
            viewModel = get<OnboardingViewModel>(),
            navigator = get<OnboardingNavigator>()
        )
    }
}