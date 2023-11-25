package com.arakim.datastructurevisaulization.data.database

import android.content.Context
import androidx.room.Room
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppDatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Const.DataStructureDataBaseName,
    )
        .fallbackToDestructiveMigration()
        .addMigrations(
            Migration_1_2,
        )
        .build()

    @Provides
    @Singleton
    fun providesDataStructureDao(
        dataStructureDataBase: AppDatabase,
    ) = dataStructureDataBase.dataStructureDao()

}