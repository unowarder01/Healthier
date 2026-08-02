package unowarder01.healthier.features.map.ui

import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.map.ui.MapContract.Action
import unowarder01.healthier.features.map.ui.MapContract.Intent
import unowarder01.healthier.features.map.ui.MapContract.State

class MapViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State
)
