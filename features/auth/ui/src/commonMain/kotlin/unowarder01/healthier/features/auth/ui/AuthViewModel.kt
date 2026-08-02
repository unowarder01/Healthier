package unowarder01.healthier.features.auth.ui

import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.auth.ui.AuthContract.Action
import unowarder01.healthier.features.auth.ui.AuthContract.Action.NavigateToCity
import unowarder01.healthier.features.auth.ui.AuthContract.Intent
import unowarder01.healthier.features.auth.ui.AuthContract.Intent.OnSocialProviderClicked
import unowarder01.healthier.features.auth.ui.AuthContract.State
import unowarder01.healthier.features.auth.ui.content.SocialProviderUi

private typealias Ctx = PipelineContext<State, Intent, Action>

class AuthViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State()
) {
    override suspend fun Ctx.handleIntent(intent: Intent) {
        when (intent) {
            is OnSocialProviderClicked -> handleSocialProviderClick(intent.provider)
        }
    }

    private suspend fun Ctx.handleSocialProviderClick(provider: SocialProviderUi) {
        action(NavigateToCity)
    }
}
