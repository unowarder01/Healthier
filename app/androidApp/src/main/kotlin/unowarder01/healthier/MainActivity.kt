package unowarder01.healthier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.retainedComponent
import com.mmk.kmpnotifier.permission.permissionUtil
import unowarder01.healthier.core.database.createAndroidClinicCache
import unowarder01.healthier.core.presentation.permissions.LocalNotificationPermissionRequester
import unowarder01.healthier.core.presentation.permissions.NotificationPermissionRequester

class MainActivity : ComponentActivity() {
    private val notificationPermissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().let { splashScreen ->
            splashScreen.setOnExitAnimationListener { listener -> listener.remove() }
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        appContent()
    }

    private fun ComponentActivity.appContent() {
        val notificationPermissionRequester = NotificationPermissionRequester(
            notificationPermissionUtil::askNotificationPermission
        )
        val root = retainedComponent { context ->
            createRootComponent(
                componentContext = context,
                runtime = AppRuntimeConfig(
                    isDebug = BuildConfig.DEBUG,
                    apiBaseUrl = BuildConfig.API_BASE_URL
                ),
                clinicCache = createAndroidClinicCache(applicationContext)
            )
        }
        setContent {
            CompositionLocalProvider(
                LocalNotificationPermissionRequester provides notificationPermissionRequester
            ) {
                App(root)
            }
        }
    }
}
