package unowarder01.healthier.core.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store

fun <S : MVIState, I : MVIIntent, A : MVIAction> ComponentContext.retainedStore(
    key: String,
    factory: () -> Store<S, I, A>
): Store<S, I, A> = instanceKeeper.getOrCreate(key) {
    StoreHolder(factory())
}.store

private class StoreHolder<S : MVIState, I : MVIIntent, A : MVIAction>(
    val store: Store<S, I, A>
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        store.start(scope)
    }

    override fun onDestroy() {
        store.close()
        scope.cancel()
    }
}
