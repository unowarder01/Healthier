package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.platform.SocialAuthProvider
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.auth.domain.usecase.AuthenticateUseCase
import unowarder01.healthier.features.auth.ui.AuthContract.Action
import unowarder01.healthier.features.auth.ui.AuthContract.Action.NavigateToCity
import unowarder01.healthier.features.auth.ui.AuthContract.Intent
import unowarder01.healthier.features.auth.ui.AuthContract.Intent.Authenticate
import unowarder01.healthier.features.auth.ui.AuthContract.Intent.Reveal
import unowarder01.healthier.features.auth.ui.AuthContract.State

private typealias Context = PipelineContext<State, Intent, Action>

class AuthViewModel(
    private val authenticate: AuthenticateUseCase,
    private val provider: SocialAuthProvider
) : BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    val availableProviders get() = provider.availableProviders

    override suspend fun Context.handleIntent(intent: Intent) {
        when (intent) {
            Reveal -> updateState { copy(visible = true) }
            is Authenticate -> {
                if (currentState().loadingProvider != null) return
                updateState { copy(loadingProvider = intent.provider, error = null) }
                when (authenticate(intent.provider)) {
                    is AppResult.Success -> {
                        updateState { copy(loadingProvider = null) }
                        action(NavigateToCity)
                    }
                    is AppResult.Failure ->
                        updateState { copy(loadingProvider = null, error = "auth_failed") }
                }
            }
        }
    }
}
