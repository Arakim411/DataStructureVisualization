package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.GetVertexInfoHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions.AddTransitionHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerPresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerState.ReadyState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.VisualizationSetUpUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@ViewModelScoped
class VisualizationBuilder @Inject constructor(
    val visualizationCore: VisualizationCorePresenter,
    val setUpPickerPresenter: VisualizationSetUpPickerPresenter,
    val addTransitionHelper: AddTransitionHelper,
    private val getVertexInfo: GetVertexInfoHelper,
) {
    lateinit var onSetUpChanged: ((VisualizationSetUpUiModel) -> Unit)
    private var isInitialized: Boolean = false

    fun initialize(
        dataStructureId: Int,
        coroutineScope: CoroutineScope,
        onInitialized: () -> Unit,
    ) {
        setUpPickerPresenter.initialize(coroutineScope)
        setUpPickerPresenter.onAction(InitializeAction(dataStructureId))

        addTransitionHelper.initialize(visualizationCore::addTransitionToQueue)
        coroutineScope.launch { listenForVisualizationSetUpUpdates(onInitialized) }
    }

    fun setOnVisualizationSetUpChanged(unit: (VisualizationSetUpUiModel) -> Unit) {
        onSetUpChanged = unit
    }

    fun createVertex(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ) {
        visualizationCore.apply {
            val vertexInfo = with(getVertexInfo) {
                invoke(
                    vertexId,
                    title,
                    position,
                    shape
                )
            }
            visualizationCore.createVertex(vertexInfo)
        }
    }

    fun createConnection(
        from: VertexId,
        to: VertexId,
    ) = visualizationCore.createConnection(from, to)

    private suspend fun listenForVisualizationSetUpUpdates(
        onInitialized: () -> Unit,
    ) {
        setUpPickerPresenter.stateFlow.collect { state ->
            if (state is ReadyState) {
                visualizationCore.setVisualizationSetUp(state.setUp)
                onSetUpChanged(state.setUp)
                if (!isInitialized) {
                    isInitialized = true
                    onInitialized()
                }
            }
        }
    }

}


