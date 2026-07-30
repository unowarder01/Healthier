package unowarder01.healthier.features.home.ui

import org.koin.dsl.module
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.StoreViewModel

class HomeStoreFactory {
    fun create() = healthierStore<HomeContract.State, HomeContract.Intent, HomeContract.Action>(
        name = "home.tabs",
        initial = HomeContract.State(),
    ) { intent ->
        when (intent) {
            is HomeContract.Intent.SelectTab ->
                if (currentState().selectedTab != intent.tab) {
                    updateState { copy(selectedTab = intent.tab) }
                }
        }
    }
}

class HomeViewModel(factory: HomeStoreFactory) :
    StoreViewModel<HomeContract.State, HomeContract.Intent, HomeContract.Action>(factory.create())

val homeUiModule = module {
    factory { HomeStoreFactory() }
}
