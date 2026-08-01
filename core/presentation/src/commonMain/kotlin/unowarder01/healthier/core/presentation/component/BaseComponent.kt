package unowarder01.healthier.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import unowarder01.healthier.core.designsystem.typealiases.ComposeState
import unowarder01.healthier.core.presentation.retainedStore
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel

interface Component

typealias SS = MVIState
typealias II = MVIIntent
typealias AA = MVIAction

abstract class BaseComponent<S : SS, I : II, A : AA, VM : BaseViewModel<S, I, A>>(
    context: ComponentContext,
    viewModel: VM
) : Component, ComponentContext by context, Store<S, I, A> by context.retainedStore(
    key = viewModel.store.name!!,
    factory = { viewModel.store }
) {
    @Composable
    abstract fun subscribeState(): ComposeState<S>
}
