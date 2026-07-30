package unowarder01.healthier.core.presentation.viewmodel

import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.Store
import unowarder01.healthier.core.mvi.healthierStore

abstract class BaseViewModel<S : MVIState, I : MVIIntent, A : MVIAction>(
    initialState: S,
    val storeKey: String
) : Container<S, I, A> {
    final override val store: Store<S, I, A> by lazy {
        healthierStore(
            name = storeKey,
            initial = initialState,
            handle = { intent -> handleIntent(intent) }
        )
    }

    protected abstract suspend fun PipelineContext<S, I, A>.handleIntent(intent: I)
}
