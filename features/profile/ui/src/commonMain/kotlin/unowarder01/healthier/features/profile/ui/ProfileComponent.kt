package unowarder01.healthier.features.profile.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.profile.ui.ProfileContract.Action
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent
import unowarder01.healthier.features.profile.ui.ProfileContract.Listener
import unowarder01.healthier.features.profile.ui.ProfileContract.State

class ProfileComponent(
    context: ComponentContext,
    viewModel: ProfileViewModel,
    private val navigator: ProfileNavigator,
) : Listener, BaseComponent<State, Intent, Action, ProfileViewModel>(
    context = context,
    viewModel = viewModel,
) {
    @Composable
    override fun subscribeState() = subscribe { }
}
