package com.arakim.datastructurevisaulization.data.database

import android.content.Context
import androidx.room.Room
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
        DataBaseName,
    ).fallbackToDestructiveMigration()
        .addMigrations(
            Migration_1_2,
            Migration_2_3,
        ).build()

    @Provides
    @Singleton
    fun providesDataStructureDao(
        dataStructureDataBase: AppDatabase,
    ) = dataStructureDataBase.dataStructureDao()

    @Provides
    @Singleton
    fun providesVisualizationSetUpDao(
        dataStructureDataBase: AppDatabase,
    ) = dataStructureDataBase.visualizationSetUpDao()
}

private const val DataBaseName = "app_database"