package unowarder01.healthier.features.splash.ui

import com.arkivanov.decompose.ComponentContext
import unowarder01.healthier.core.presentation.retainedStore

class SplashComponent(
    componentContext: ComponentContext,
    factory: SplashStoreFactory,
    private val navigator: SplashNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("splash.language", factory::create)

    fun handle(action: SplashContract.Action) {
        when (action) {
            SplashContract.Action.NavigateToAuth -> navigator.openAuth()
        }
    }
}
