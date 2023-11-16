package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.di

import android.content.Context
import androidx.room.Room
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.database.DataStructureDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataStructureDataBaseModule {

    @Provides
    @Singleton
    fun providesDataStructureDataBase(
        @ApplicationContext context: Context,
    ): DataStructureDataBase = Room.databaseBuilder(
        context,
        DataStructureDataBase::class.java,
        Const.DataStructureDataBaseName
    ).build()

    @Provides
    @Singleton
    fun providesDataStructureDao(
        dataStructureDataBase: DataStructureDataBase,
    ) = dataStructureDataBase.dataStructureDao()

}