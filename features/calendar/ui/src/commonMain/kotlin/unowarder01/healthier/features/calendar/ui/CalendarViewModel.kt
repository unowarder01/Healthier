package unowarder01.healthier.features.calendar.ui

import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.calendar.ui.CalendarContract.Action
import unowarder01.healthier.features.calendar.ui.CalendarContract.Intent
import unowarder01.healthier.features.calendar.ui.CalendarContract.State

class CalendarViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State
)
