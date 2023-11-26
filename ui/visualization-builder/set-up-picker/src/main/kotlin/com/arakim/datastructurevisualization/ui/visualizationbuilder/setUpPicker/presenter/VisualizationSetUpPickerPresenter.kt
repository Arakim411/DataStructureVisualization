package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter

import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerPresenter
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerSideEffect.ItemUpdatedSideEffect
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.InitializationAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.VisualizationSetUpUpdatedAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerState.IdleState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.HandleVisualizationSetUpUpdated
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.InitializationReducer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal typealias State = VisualizationSetUpPickerState
internal typealias Action = VisualizationSetUpPickerAction
internal typealias SideEffect = VisualizationSetUpPickerSideEffect

class VisualizationSetUpPickerPresenter @Inject constructor(
    val genericPickerPresenter: GenericPickerPresenter,
    initializationReducer: InitializationReducer,
    handleVisualizationSetUpUpdated: HandleVisualizationSetUpUpdated,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        initializationReducer.setGenericPickerPresenter(genericPickerPresenter)
        registerReducer<InitializationAction>(initializationReducer)
        registerReducer<VisualizationSetUpUpdatedAction>(handleVisualizationSetUpUpdated)

    }

    override fun onInitialized() {
        super.onInitialized()
        genericPickerPresenter.initialize(coroutineScope)
        listenForGenericPickerUpdates()
    }

    private fun listenForGenericPickerUpdates() {
        genericPickerPresenter.sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                is ItemUpdatedSideEffect -> onAction(VisualizationSetUpUpdatedAction(sideEffect.item))
            }
        }.launchIn(coroutineScope)
    }

}