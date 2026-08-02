package unowarder01.healthier.features.calendar.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.presentation.component.BaseComponent
import unowarder01.healthier.features.calendar.ui.CalendarContract.Action
import unowarder01.healthier.features.calendar.ui.CalendarContract.Intent
import unowarder01.healthier.features.calendar.ui.CalendarContract.Listener
import unowarder01.healthier.features.calendar.ui.CalendarContract.State

class CalendarComponent(
    context: ComponentContext,
    viewModel: CalendarViewModel,
    private val navigator: CalendarNavigator,
) : Listener, BaseComponent<State, Intent, Action, CalendarViewModel>(
    context = context,
    viewModel = viewModel,
) {
    @Composable
    override fun subscribeState() = subscribe { }
}
