package unowarder01.healthier.features.map.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.map.ui.MapContract.Action
import unowarder01.healthier.features.map.ui.MapContract.Intent
import unowarder01.healthier.features.map.ui.MapContract.Listener
import unowarder01.healthier.features.map.ui.MapContract.State

class MapComponent(
    context: ComponentContext,
    viewModel: MapViewModel,
    private val navigator: MapNavigator,
) : Listener, BaseComponent<State, Intent, Action, MapViewModel>(
    context = context,
    viewModel = viewModel,
) {
    @Composable
    override fun subscribeState() = subscribe { }
}
