package unowarder01.healthier.core.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import kotlinx.coroutines.test.runTest
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSUUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
class RoomClinicCacheTest {
    @Test
    fun daoReplacesAndReadsOnlyRequestedCity() = runTest {
        val database = buildHealthierDatabase(
            Room.databaseBuilder<HealthierDatabase>(name = temporaryDatabasePath())
        )
        val cache = RoomClinicCache(database.clinicDao())
        val tbilisi = ClinicCacheRecord("1", "tbilisi", "A", "Care", "One", 1.0, 2.0, null)
        val batumi = ClinicCacheRecord("2", "batumi", "B", "Care", "Two", 3.0, 4.0, null)

        cache.replace("tbilisi", listOf(tbilisi))
        cache.replace("batumi", listOf(batumi))
        cache.replace("tbilisi", listOf(tbilisi.copy(name = "Updated")))

        assertEquals(listOf("Updated"), cache.clinics("tbilisi").map(ClinicCacheRecord::name))
        assertEquals(listOf("B"), cache.clinics("batumi").map(ClinicCacheRecord::name))
        database.close()
    }

    @Test
    fun migrationOneToTwoAddsTimestampColumn() {
        val connection = BundledSQLiteDriver().open(temporaryDatabasePath())
        connection.execSQL(
            """
            CREATE TABLE clinics (
                id TEXT NOT NULL PRIMARY KEY,
                cityId TEXT NOT NULL,
                name TEXT NOT NULL,
                specialization TEXT NOT NULL,
                address TEXT NOT NULL,
                latitude REAL NOT NULL,
                longitude REAL NOT NULL,
                imageUrl TEXT
            )
            """.trimIndent()
        )

        MIGRATION_1_2.migrate(connection)

        val statement = connection.prepare("PRAGMA table_info(clinics)")
        val columns = buildList {
            while (statement.step()) add(statement.getText(1))
        }
        statement.close()
        connection.close()
        assertTrue("updatedAtEpochMillis" in columns)
    }
}

private fun temporaryDatabasePath(): String =
    "${NSTemporaryDirectory()}healthier-${NSUUID().UUIDString}.db"
