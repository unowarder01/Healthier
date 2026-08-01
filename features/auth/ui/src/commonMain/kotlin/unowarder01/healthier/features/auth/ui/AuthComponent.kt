package unowarder01.healthier.features.auth.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.auth.ui.AuthContract.Action
import unowarder01.healthier.features.auth.ui.AuthContract.Action.NavigateToCity
import unowarder01.healthier.features.auth.ui.AuthContract.Intent
import unowarder01.healthier.features.auth.ui.AuthContract.Listener
import unowarder01.healthier.features.auth.ui.AuthContract.State

class AuthComponent(
    context: ComponentContext,
    viewModel: AuthViewModel,
    private val navigator: AuthNavigator
) : Listener, BaseComponent<State, Intent, Action, AuthViewModel>(
    context = context,
    viewModel = viewModel
) {
    /**
     * STATE
     */
    @Composable
    override fun subscribeState() = subscribe { action ->
        when (action) {
            NavigateToCity -> navigator.openCity()
        }
    }

    /**
     * LISTENER
     */
}
