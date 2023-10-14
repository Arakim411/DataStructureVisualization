package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.di

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface VisualizationEngineModule {

    @Binds
    fun bindVisualizationEnginePresenter(
        visualizationEnginePresenterImpl: VisualizationEnginePresenterImpl
    ): VisualizationEnginePresenter
}