package com.arakim.datastructurevisualization.data.repository.dataStructure.di

import com.arakim.datastructurevisualization.data.repository.dataStructure.DataStructureRepositoryImpl
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface DataStructureModule {

    @Binds
    @ActivityRetainedScoped
    fun bindDataStructureRepository(impl: DataStructureRepositoryImpl): DataStructureRepository

}