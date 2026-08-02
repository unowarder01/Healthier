package unowarder01.healthier.features.health.ui

import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.health.ui.HealthContract.Action
import unowarder01.healthier.features.health.ui.HealthContract.Intent
import unowarder01.healthier.features.health.ui.HealthContract.State

class HealthViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State
)
