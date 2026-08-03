package unowarder01.healthier

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import unowarder01.healthier.di.appModules
import unowarder01.healthier.extensions.initKoin

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() = initKoin(
        modules = appModules,
        platformCallback = {
            androidContext(this@Application)
            androidLogger(level = Level.ERROR)
        }
    )
}