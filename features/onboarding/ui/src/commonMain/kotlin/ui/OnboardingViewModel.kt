package ui

import ui.OnboardingContract.Action
import ui.OnboardingContract.Intent
import ui.OnboardingContract.State
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel

class OnboardingViewModel: BaseViewModel<State, Intent, Action>(
    initialState = State()
)