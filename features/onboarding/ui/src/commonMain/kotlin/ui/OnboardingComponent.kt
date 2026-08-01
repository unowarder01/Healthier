package ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import ui.OnboardingContract.Action
import ui.OnboardingContract.Intent
import ui.OnboardingContract.Listener
import ui.OnboardingContract.State
import unowarder01.healthier.core.presentation.component.BaseComponent

class OnboardingComponent(
    context: ComponentContext,
    viewModel: OnboardingViewModel,
    private val navigator: OnboardingNavigator
): Listener, BaseComponent<State, Intent, Action, OnboardingViewModel>(
    context = context,
    viewModel = viewModel
) {
    @Composable
    override fun subscribeState() = subscribe()
}