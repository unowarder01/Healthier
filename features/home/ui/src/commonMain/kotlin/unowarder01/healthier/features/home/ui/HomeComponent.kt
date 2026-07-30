package unowarder01.healthier.features.home.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.lifecycle.doOnDestroy
import unowarder01.healthier.core.presentation.retainedStore
import unowarder01.healthier.features.home.domain.HomeTab

class HomeComponent(
    componentContext: ComponentContext,
    factory: HomeStoreFactory,
    val navigator: HomeNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("home.tabs", factory::create)

    private val tabBackCallback = BackCallback(isEnabled = false, onBack = ::returnToHealth)

    private fun returnToHealth() {
        tabBackCallback.isEnabled = false
        store.intent(HomeContract.Intent.SelectTab(HomeTab.Health))
    }

    init {
        backHandler.register(tabBackCallback)
        lifecycle.doOnDestroy {
            backHandler.unregister(tabBackCallback)
        }
    }

    fun selectTab(tab: HomeTab) {
        tabBackCallback.isEnabled = tab != HomeTab.Health
        store.intent(HomeContract.Intent.SelectTab(tab))
    }
}
