package unowarder01.healthier.core.database

import androidx.room.Room
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun createIosClinicCache(): ClinicCache {
    val directory = NSFileManager.defaultManager
        .URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null,
        )
        ?.path
        ?: error("Documents directory is unavailable")
    val database = buildHealthierDatabase(
        Room.databaseBuilder<HealthierDatabase>(name = "$directory/healthier.db")
    )
    return RoomClinicCache(database.clinicDao())
}
