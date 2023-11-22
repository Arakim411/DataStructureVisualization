package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers

import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.Action
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.SaveAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeSideEffect.SavedSideEffect
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.SideEffect
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.State
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.SaveDataStructureUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveReducer @Inject constructor(
    private val visualizationBuilder: BinarySearchTreeVisualizationBuilder,
    private val saveDataStructureUseCase: SaveDataStructureUseCase,
) : StateReducerWithSideEffect<State, Action, SaveAction, SideEffect>() {

    private var saveJob: Job? = null

    override fun State.reduce(action: SaveAction): State = when (this) {
        is ReadyState -> reduceSaveAction()
        else -> logInvalidState()
    }

    private fun ReadyState.reduceSaveAction(): State {
        saveJob?.cancel()
        saveJob = coroutineScope.launch {
            saveDataStructureState(id)
            emitSideEffect(SavedSideEffect)
        }
        return this
    }

    private suspend fun saveDataStructureState(id: Int){
    val dataStructureJson = visualizationBuilder.serializeToJson()
        saveDataStructureUseCase(id, dataStructureJson)
    }
}