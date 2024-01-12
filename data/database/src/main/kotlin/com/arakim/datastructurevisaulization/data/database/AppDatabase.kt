package com.arakim.datastructurevisaulization.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.VisualizationSetUpDao
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.VisualizationSetUpEntity
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.VisualizationSetUpTypeConverter
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.DataStructureDao
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureEntity

@Database(
    entities = [
        DataStructureEntity::class,
        VisualizationSetUpEntity::class,
    ],
    version = 3,
)
@TypeConverters(VisualizationSetUpTypeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun dataStructureDao(): DataStructureDao

    abstract fun visualizationSetUpDao(): VisualizationSetUpDao
}

internal val Migration_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ${Const.DataStructureEntityName} ADD COLUMN is_favorite INTEGER DEFAULT 0 NOT NULL")
    }
}

internal val Migration_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ${Const.DataStructureEntityName} ADD COLUMN deletion_date_utc INTEGER")
    }
}