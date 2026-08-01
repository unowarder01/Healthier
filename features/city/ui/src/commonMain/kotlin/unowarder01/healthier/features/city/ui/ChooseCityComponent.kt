package unowarder01.healthier.features.city.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.city.ui.ChooseCityContract.Action
import unowarder01.healthier.features.city.ui.ChooseCityContract.Action.NavigateHome
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent.Load
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent.QueryChanged
import unowarder01.healthier.features.city.ui.ChooseCityContract.Intent.SelectCity
import unowarder01.healthier.features.city.ui.ChooseCityContract.Listener
import unowarder01.healthier.features.city.ui.ChooseCityContract.State

class ChooseCityComponent(
    context: ComponentContext,
    viewModel: ChooseCityViewModel,
    private val navigator: ChooseCityNavigator
) : BaseComponent<
    State,
    Intent,
    Action,
    ChooseCityViewModel
>(
    context = context,
    viewModel = viewModel
), Listener {
    @Composable
    override fun subscribeState() = subscribe { action -> handle(action) }

    override fun onScreenShown() {
        intent(Load)
    }

    override fun onQueryChanged(query: String) {
        intent(QueryChanged(query))
    }

    override fun onCitySelected(cityId: String) {
        intent(SelectCity(cityId))
    }

    fun handle(action: Action) {
        when (action) {
            is NavigateHome -> navigator.openHome(action.clinics)
        }
    }
}
