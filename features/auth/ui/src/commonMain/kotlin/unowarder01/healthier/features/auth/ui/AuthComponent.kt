package unowarder01.healthier.features.auth.ui

import com.arkivanov.decompose.ComponentContext
import unowarder01.healthier.core.platform.SocialProvider
import unowarder01.healthier.core.presentation.retainedStore

class AuthComponent(
    componentContext: ComponentContext,
    factory: AuthStoreFactory,
    private val navigator: AuthNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("auth.social", factory::create)
    val providers: Set<SocialProvider> = factory.availableProviders

    fun handle(action: AuthContract.Action) {
        when (action) {
            AuthContract.Action.NavigateToCity -> navigator.openCity()
        }
    }
}
