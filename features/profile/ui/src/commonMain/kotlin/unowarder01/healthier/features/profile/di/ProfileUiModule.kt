package unowarder01.healthier.features.profile.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.features.profile.ui.ProfileComponent
import unowarder01.healthier.features.profile.ui.ProfileViewModel

val profileUiModule = module {
    factory { (language: AppLanguage, theme: AppTheme) ->
        ProfileViewModel(
            repository = get(),
            updateProfile = get(),
            updateLanguage = get(),
            updateTheme = get(),
            language = language,
            theme = theme
        )
    }
    factory { (context: ComponentContext, language: AppLanguage, theme: AppTheme) ->
        ProfileComponent(
            context = context,
            viewModel = get {
                org.koin.core.parameter.parametersOf(language, theme)
            },
            navigator = get()
        )
    }
}
