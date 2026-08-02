package unowarder01.healthier.features.city.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.city.ui.CityContract.Action
import unowarder01.healthier.features.city.ui.CityContract.Intent
import unowarder01.healthier.features.city.ui.CityContract.Listener
import unowarder01.healthier.features.city.ui.CityContract.State

class CityComponent(
    context: ComponentContext,
    viewModel: CityViewModel,
    private val navigator: CityNavigator,
) : Listener, BaseComponent<State, Intent, Action, CityViewModel>(
    context = context,
    viewModel = viewModel,
) {
    @Composable
    override fun subscribeState() = subscribe { }
}
