package unowarder01.healthier.features.map.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.platform.MapAvailability
import unowarder01.healthier.core.platform.MapRenderer
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.map.ui.MapContract.Action
import unowarder01.healthier.features.map.ui.MapContract.Intent
import unowarder01.healthier.features.map.ui.MapContract.Intent.DismissClinic
import unowarder01.healthier.features.map.ui.MapContract.Intent.SelectClinic
import unowarder01.healthier.features.map.ui.MapContract.Listener
import unowarder01.healthier.features.map.ui.MapContract.State

class MapComponent(
    context: ComponentContext,
    viewModel: MapViewModel,
    renderer: MapRenderer,
    val navigator: MapNavigator
) : BaseComponent<
    State,
    Intent,
    Action,
    MapViewModel
>(
    context = context,
    viewModel = viewModel
), Listener {
    val availability: MapAvailability = renderer.availability

    @Composable
    override fun subscribeState() = subscribe()

    override fun onClinicSelected(clinicId: String) {
        intent(SelectClinic(clinicId))
    }

    override fun onClinicDismissed() {
        intent(DismissClinic)
    }
}
