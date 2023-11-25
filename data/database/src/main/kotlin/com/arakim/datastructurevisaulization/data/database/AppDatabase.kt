package com.arakim.datastructurevisaulization.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.DataStructureDao
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureDto

@Database(
    entities = [DataStructureDto::class],
    version = 2,
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun dataStructureDao(): DataStructureDao

}

internal val Migration_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ${Const.DataStructureEntityName} ADD COLUMN is_favorite INTEGER DEFAULT 0 NOT NULL")
    }
}