package unowarder01.healthier.core.database

import android.content.Context
import androidx.room.Room

fun createAndroidClinicCache(context: Context): ClinicCache {
    val database = buildHealthierDatabase(
        Room.databaseBuilder<HealthierDatabase>(
            context = context.applicationContext,
            name = context.getDatabasePath("healthier.db").absolutePath,
        )
    )
    return RoomClinicCache(database.clinicDao())
}
