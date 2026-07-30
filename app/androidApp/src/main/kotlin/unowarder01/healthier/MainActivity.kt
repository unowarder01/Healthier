package unowarder01.healthier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import unowarder01.healthier.core.database.createAndroidClinicCache

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root = retainedComponent { context ->
            createRootComponent(
                context,
                AppRuntimeConfig(
                    isDebug = BuildConfig.DEBUG,
                    apiBaseUrl = BuildConfig.API_BASE_URL
                ),
                createAndroidClinicCache(applicationContext)
            )
        }
        setContent { App(root) }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Preview is hosted by the running app because RootComponent requires a lifecycle.
}
