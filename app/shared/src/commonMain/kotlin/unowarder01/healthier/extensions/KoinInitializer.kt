package unowarder01.healthier.extensions

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(
    modules: List<Module>,
    platformCallback: KoinApplication.() -> Unit = {}
): KoinApplication = startKoin {
    modules(modules)
    platformCallback()
}
