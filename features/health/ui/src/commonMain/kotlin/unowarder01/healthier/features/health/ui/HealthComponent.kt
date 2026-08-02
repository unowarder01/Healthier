package unowarder01.healthier.features.health.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.health.ui.HealthContract.Action
import unowarder01.healthier.features.health.ui.HealthContract.Intent
import unowarder01.healthier.features.health.ui.HealthContract.Listener
import unowarder01.healthier.features.health.ui.HealthContract.State

class HealthComponent(
    context: ComponentContext,
    viewModel: HealthViewModel,
    private val navigator: HealthNavigator,
) : Listener, BaseComponent<State, Intent, Action, HealthViewModel>(
    context = context,
    viewModel = viewModel,
) {
    @Composable
    override fun subscribeState() = subscribe { }
}
