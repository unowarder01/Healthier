package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.platform.SocialAuthProvider
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.auth.domain.usecase.AuthenticateUseCase
import unowarder01.healthier.features.auth.ui.AuthContract.Action
import unowarder01.healthier.features.auth.ui.AuthContract.Intent
import unowarder01.healthier.features.auth.ui.AuthContract.State

private typealias Context = PipelineContext<State, Intent, Action>

class AuthViewModel(
    private val authenticate: AuthenticateUseCase,
    private val provider: SocialAuthProvider
) : BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Context.handleIntent(intent: Intent) {

    }
}
