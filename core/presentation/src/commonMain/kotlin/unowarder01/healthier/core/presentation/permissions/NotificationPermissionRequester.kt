package unowarder01.healthier.core.presentation.permissions

import androidx.compose.runtime.staticCompositionLocalOf

fun interface NotificationPermissionRequester {
    fun request(onResult: (Boolean) -> Unit)
}

val LocalNotificationPermissionRequester = staticCompositionLocalOf {
    NotificationPermissionRequester { onResult -> onResult(false) }
}
