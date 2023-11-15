package com.arakim.datastructurevisualization.data.repository.dataStructure.di

import com.arakim.datastructurevisualization.data.repository.dataStructure.DataStructureRepositoryImpl
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface DataStructureModule {

    @Binds
    fun bindDataStructureRepository(impl: DataStructureRepositoryImpl): DataStructureRepository

}