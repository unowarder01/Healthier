package unowarder01.healthier.features.auth.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import unowarder01.healthier.features.auth.ui.AuthComponent
import unowarder01.healthier.features.auth.ui.AuthViewModel

val authUiModule = module {
    factory { AuthViewModel() }
    factory { (context: ComponentContext) ->
        AuthComponent(
            context = context,
            viewModel = get()
        )
    }
}
