package unowarder01.healthier.core.database

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.Transaction
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import kotlinx.coroutines.Dispatchers

@Entity(tableName = "clinics", primaryKeys = ["id"])
data class ClinicEntity(
    val id: String,
    val cityId: String,
    val name: String,
    val specialization: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String?,
    val updatedAtEpochMillis: Long = 0,
)

@Dao
interface ClinicDao {
    @Query("SELECT * FROM clinics WHERE cityId = :cityId ORDER BY name")
    suspend fun clinics(cityId: String): List<ClinicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(values: List<ClinicEntity>)

    @Query("DELETE FROM clinics WHERE cityId = :cityId")
    suspend fun deleteForCity(cityId: String)

    @Transaction
    suspend fun replace(cityId: String, values: List<ClinicEntity>) {
        deleteForCity(cityId)
        insertAll(values)
    }
}

@Database(
    entities = [ClinicEntity::class],
    version = 2,
    exportSchema = true,
)
@ConstructedBy(HealthierDatabaseConstructor::class)
abstract class HealthierDatabase : RoomDatabase() {
    abstract fun clinicDao(): ClinicDao
}

@Suppress("KotlinNoActualForExpect")
expect object HealthierDatabaseConstructor : RoomDatabaseConstructor<HealthierDatabase> {
    override fun initialize(): HealthierDatabase
}

fun buildHealthierDatabase(builder: RoomDatabase.Builder<HealthierDatabase>): HealthierDatabase =
    builder
        .addMigrations(MIGRATION_1_2)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()

class RoomClinicCache(
    private val dao: ClinicDao,
) : ClinicCache {
    override suspend fun clinics(cityId: String): List<ClinicCacheRecord> =
        dao.clinics(cityId).map(ClinicEntity::toRecord)

    override suspend fun replace(cityId: String, clinics: List<ClinicCacheRecord>) {
        dao.replace(cityId, clinics.map(ClinicCacheRecord::toEntity))
    }
}

private fun ClinicEntity.toRecord() = ClinicCacheRecord(
    id, cityId, name, specialization, address, latitude, longitude, imageUrl
)

private fun ClinicCacheRecord.toEntity() = ClinicEntity(
    id, cityId, name, specialization, address, latitude, longitude, imageUrl
)

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "ALTER TABLE clinics ADD COLUMN updatedAtEpochMillis INTEGER NOT NULL DEFAULT 0"
        )
    }
}
