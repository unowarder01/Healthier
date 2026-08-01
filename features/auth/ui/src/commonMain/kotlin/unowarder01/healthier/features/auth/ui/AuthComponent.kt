package unowarder01.healthier.features.auth.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.platform.SocialProvider
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.auth.ui.AuthContract.Action
import unowarder01.healthier.features.auth.ui.AuthContract.Action.NavigateToCity
import unowarder01.healthier.features.auth.ui.AuthContract.Intent
import unowarder01.healthier.features.auth.ui.AuthContract.Intent.Authenticate
import unowarder01.healthier.features.auth.ui.AuthContract.Intent.Reveal
import unowarder01.healthier.features.auth.ui.AuthContract.Listener
import unowarder01.healthier.features.auth.ui.AuthContract.State

class AuthComponent(
    context: ComponentContext,
    viewModel: AuthViewModel,
    private val navigator: AuthNavigator
) : BaseComponent<
    State,
    Intent,
    Action,
    AuthViewModel
>(
    context = context,
    viewModel = viewModel
), Listener {
    val providers: Set<SocialProvider> = viewModel.availableProviders

    @Composable
    override fun subscribeState() = subscribe { action -> handle(action) }

    override fun onScreenShown() {
        intent(Reveal)
    }

    override fun onProviderSelected(provider: SocialProvider) {
        intent(Authenticate(provider))
    }

    fun handle(action: Action) {
        when (action) {
            NavigateToCity -> navigator.openCity()
        }
    }
}
