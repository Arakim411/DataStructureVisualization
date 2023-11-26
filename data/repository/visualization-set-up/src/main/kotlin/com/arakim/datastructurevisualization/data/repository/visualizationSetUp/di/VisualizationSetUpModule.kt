package com.arakim.datastructurevisualization.data.repository.visualizationSetUp.di

import com.arakim.datastructurevisualization.data.repository.visualizationSetUp.VisualizationSetUpRepositoryImpl
import com.arakim.datastructurevisualization.domain.visualizationSetUp.VisualizationSetUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class VisualizationSetUpModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindsVisualizationSetUpRepository(
        repository: VisualizationSetUpRepositoryImpl
    ): VisualizationSetUpRepository

}