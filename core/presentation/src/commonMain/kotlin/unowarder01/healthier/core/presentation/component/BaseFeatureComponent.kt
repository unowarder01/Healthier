package unowarder01.healthier.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import unowarder01.healthier.core.presentation.retainedStore
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel

interface Component

abstract class BaseFeatureComponent<S : MVIState, I : MVIIntent, A : MVIAction, VM : BaseViewModel<S, I, A>>(
    context: ComponentContext,
    viewModel: VM
) : ComponentContext by context,
    Component,
    Store<S, I, A> by context.retainedStore(
        key = viewModel.storeKey,
        factory = { viewModel.store }
    ) {
    @Composable
    abstract fun subscribeState(): State<S>
}
