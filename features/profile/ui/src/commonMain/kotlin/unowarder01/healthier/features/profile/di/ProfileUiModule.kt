package unowarder01.healthier.features.profile.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.profile.ui.ProfileComponent
import unowarder01.healthier.features.profile.ui.ProfileNavigator
import unowarder01.healthier.features.profile.ui.ProfileViewModel

val profileUiModule = module {
    factoryOf(::ProfileViewModel)
    factory { (context: ComponentContext) ->
        ProfileComponent(
            context = context,
            viewModel = get(),
            navigator = get<ProfileNavigator>(),
        )
    }
}
