package unowarder01.healthier.features.splash.ui

import kotlinx.coroutines.delay
import org.koin.dsl.module
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.StoreViewModel
import unowarder01.healthier.features.splash.domain.SelectLanguageUseCase

class SplashStoreFactory(
    private val selectLanguage: SelectLanguageUseCase,
) {
    fun create() = healthierStore<SplashContract.State, SplashContract.Intent, SplashContract.Action>(
        name = "splash.language",
        initial = SplashContract.State(),
    ) { intent ->
        when (intent) {
            SplashContract.Intent.RevealLanguages ->
                updateState { copy(showLanguages = true) }

            is SplashContract.Intent.SelectLanguage -> {
                if (currentState().exiting) return@healthierStore
                selectLanguage(intent.language)
                updateState { copy(selected = intent.language, exiting = true) }
                delay(220)
                action(SplashContract.Action.NavigateToAuth)
            }
        }
    }
}

class SplashViewModel(storeFactory: SplashStoreFactory) :
    StoreViewModel<SplashContract.State, SplashContract.Intent, SplashContract.Action>(
        storeFactory.create()
    )

val splashUiModule = module {
    factory { SplashStoreFactory(get()) }
}
