package unowarder01.healthier.features.health.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.health.ui.HealthContract.Action
import unowarder01.healthier.features.health.ui.HealthContract.Intent
import unowarder01.healthier.features.health.ui.HealthContract.Intent.Load
import unowarder01.healthier.features.health.ui.HealthContract.Intent.QueryChanged
import unowarder01.healthier.features.health.ui.HealthContract.Listener
import unowarder01.healthier.features.health.ui.HealthContract.State

class HealthComponent(
    context: ComponentContext,
    viewModel: HealthViewModel,
    val navigator: HealthNavigator
) : BaseComponent<
    State,
    Intent,
    Action,
    HealthViewModel
>(
    context = context,
    viewModel = viewModel
), Listener {
    @Composable
    override fun subscribeState() = subscribe()

    override fun onScreenShown() {
        intent(Load)
    }

    override fun onQueryChanged(query: String) {
        intent(QueryChanged(query))
    }

    override fun onLocationChangeRequested() {
        navigator.changeLocation()
    }

    override fun onLanguageChangeRequested() {
        navigator.changeLanguage()
    }
}
