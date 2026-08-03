package unowarder01.healthier.core.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AppDispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}

internal data class AppDispatchersImpl(
    override val io: CoroutineDispatcher = Dispatchers.Default, // TODO: Wait for IO in commonMain
    override val main: CoroutineDispatcher = Dispatchers.Main,
    override val default: CoroutineDispatcher = Dispatchers.Default
) : AppDispatchers