package unowarder01.healthier.features.profile.ui

import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.profile.ui.ProfileContract.Action
import unowarder01.healthier.features.profile.ui.ProfileContract.Intent
import unowarder01.healthier.features.profile.ui.ProfileContract.State

class ProfileViewModel : BaseViewModel<State, Intent, Action>(
    initialState = State
)
