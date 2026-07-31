package unowarder01.healthier.core.mvi

import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.StateStrategy
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.dsl.store
import pro.respawn.flowmvi.plugins.init
import pro.respawn.flowmvi.plugins.reduce

fun <S : MVIState, I : MVIIntent, A : MVIAction> healthierStore(
    name: String,
    initial: S,
    handleInit: suspend PipelineContext<S, I, A>.() -> Unit,
    handleIntents: suspend PipelineContext<S, I, A>.(I) -> Unit
): Store<S, I, A> = store(initial) {
    configure {
        this.name = name
        parallelIntents = false
        stateStrategy = StateStrategy.Atomic(reentrant = true)
        debuggable = false
    }
    init {
        handleInit()
    }
    reduce { intent ->
        handleIntents(intent)
    }
}

suspend fun <S : MVIState, I : MVIIntent, A : MVIAction> PipelineContext<S, I, A>.currentState(): S {
    var snapshot: S? = null
    withState { snapshot = this }
    return requireNotNull(snapshot)
}
