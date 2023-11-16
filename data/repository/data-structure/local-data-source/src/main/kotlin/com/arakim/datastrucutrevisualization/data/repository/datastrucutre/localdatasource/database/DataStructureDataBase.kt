package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureDto

@Database(entities = [DataStructureDto::class], version = 1)
internal abstract class DataStructureDataBase: RoomDatabase() {

    abstract fun dataStructureDao(): DataStructureDao

}