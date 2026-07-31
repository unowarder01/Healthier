package unowarder01.healthier.core.presentation.viewmodel

import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.StateStrategy
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.dsl.store
import pro.respawn.flowmvi.plugins.init
import pro.respawn.flowmvi.plugins.reduce
import unowarder01.healthier.core.mvi.healthierStore

abstract class BaseViewModel<S : MVIState, I : MVIIntent, A : MVIAction>(
    initialState: S
) : Container<S, I, A> {
    /**
     * STORE
     */
    final override val store: Store<S, I, A> by lazy {
        store(initialState) {
            configure {
                name = this::class.simpleName!!
                parallelIntents = false
                stateStrategy = StateStrategy.Atomic(reentrant = true)
                debuggable = false
            }
            init {
                init()
            }
            reduce { intent ->
                handleIntent(intent)
            }
        }
    }

    /**
     * BASE FUNCTIONS
     */
    protected open suspend fun PipelineContext<S, I, A>.init() {}
    protected open suspend fun PipelineContext<S, I, A>.handleIntent(intent: I) {}
}
