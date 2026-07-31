package unowarder01.healthier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.retainedComponent
import unowarder01.healthier.core.database.createAndroidClinicCache

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().let { splashScreen ->
            splashScreen.setOnExitAnimationListener { listener -> listener.remove() }
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        appContent()
    }
}

private fun ComponentActivity.appContent() {
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
    setContent { App(root) }
}