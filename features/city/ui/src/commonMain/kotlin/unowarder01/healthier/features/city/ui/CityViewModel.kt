package unowarder01.healthier.features.city.ui

import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.city.ui.CityContract.Action
import unowarder01.healthier.features.city.ui.CityContract.Intent
import unowarder01.healthier.features.city.ui.CityContract.State

class CityViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State
)
