package unowarder01.healthier.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import unowarder01.healthier.core.common.dispatchers.AppDispatchers

open class BaseRepository: KoinComponent {
    private val dispatchers by inject<AppDispatchers>()

    protected suspend fun <T> io(action: suspend CoroutineScope.() -> T): T {
        return withContext(dispatchers.io) {
            action()
        }
    }
}