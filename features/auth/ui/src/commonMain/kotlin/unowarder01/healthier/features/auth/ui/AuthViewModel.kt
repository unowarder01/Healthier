package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.auth.ui.AuthContract.Action
import unowarder01.healthier.features.auth.ui.AuthContract.Intent
import unowarder01.healthier.features.auth.ui.AuthContract.State

private typealias Context = PipelineContext<State, Intent, Action>

class AuthViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Context.handleIntent(intent: Intent) = Unit
}
