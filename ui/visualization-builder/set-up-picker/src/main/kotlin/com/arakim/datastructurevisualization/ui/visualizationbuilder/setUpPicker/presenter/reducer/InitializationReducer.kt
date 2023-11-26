package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer

import com.arakim.datastructurevisualization.domain.visualizationSetUp.useCases.CreateDefaultVisualizationSetUpUseCase
import com.arakim.datastructurevisualization.domain.visualizationSetUp.useCases.GetVisualizationSetUpUseCase
import com.arakim.datastructurevisualization.domain.visualizationSetUp.useCases.ListenForVisualizationSetUpUpdatesUseCase
import com.arakim.datastructurevisualization.kotlinutil.getOrNull
import com.arakim.datastructurevisualization.kotlinutil.onSuccess
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerPresenter
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.util.StringWrapper
import com.arakim.datastructurevisualization.ui.util.StringWrapper.StringResources
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.Action
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.State
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.InitializationAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.InitializationAction.SetUpUpdatedAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerState.InitializingState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerState.ReadyState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.DrawColorsUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.DrawSizesUiModelDp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.VisualizationSetUpUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.toUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ArrowColorId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ArrowSize
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.BackgroundColorId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.CircleRadiusId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ComparisonShapeColorId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ComparisonTimeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ConnectionLineColorId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ConnectionLineStrokeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ElementShapeColorId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ElementStrokeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.SquareEdgeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.TextColorId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.TextSizeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.VertexTimeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setuppicker.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitializationReducer @Inject constructor(
    private val listenForSetUpUpdates: ListenForVisualizationSetUpUpdatesUseCase,
    private val getSetUp: GetVisualizationSetUpUseCase,
    private val createDefaultSetUp: CreateDefaultVisualizationSetUpUseCase,
) : StateReducer<State, Action, InitializationAction>() {

    private lateinit var genericPickerPresenter: GenericPickerPresenter

    private var listenForUpdatesJob: Job? = null

    fun setGenericPickerPresenter(genericPickerPresenter: GenericPickerPresenter) {
        this.genericPickerPresenter = genericPickerPresenter
    }

    override fun State.reduce(action: InitializationAction): State = when (action) {
        is InitializeAction -> reduceInitializeAction(action)
        is SetUpUpdatedAction -> reduceSetUpUpdatedAction(action)
    }

    private fun State.reduceInitializeAction(action: InitializeAction): State {

        listenForVisualizationSetUpUpdates(action.id)

        return InitializingState
    }

    private fun listenForVisualizationSetUpUpdates(id: Int) {
        listenForUpdatesJob?.cancel()
        listenForUpdatesJob = coroutineScope.launch {
            createDefaultSetUpIfNotExists(id)
            listenForSetUpUpdates(id).collectLatest {
                it.onSuccess { setUp ->
                    onAction(SetUpUpdatedAction(setUp.toUiModel()))
                }
            }
        }
    }

    private suspend fun createDefaultSetUpIfNotExists(id: Int) {
        val setUp = getSetUp(id).getOrNull()
        if (setUp == null) {
            createDefaultSetUp(id)
        }
    }

    private fun State.reduceSetUpUpdatedAction(action: SetUpUpdatedAction): State {
        val setUp = action.setUp
        val transitionGroup = StringResources(R.string.set_up_picker_group_transition)
        val sizeGroup = StringResources(R.string.set_up_picker_group_sizes)
        val colorGroup = StringResources(R.string.set_up_picker_group_color)


        val genericPickerItems = mutableListOf<GenericPickerItem<*>>().apply {
            addAll(setUp.genericPickerTransitionItems(transitionGroup))
            addAll(setUp.drawConfig.sizes.genericPickerSizesItems(sizeGroup))
            addAll(setUp.drawConfig.colors.genericPickerColorItems(colorGroup))
        }.mapToImmutable { it }

        genericPickerPresenter.onAction(
            GenericPickerAction.InitializedAction(
                allItems = genericPickerItems,
                floatingModalItems = immutableListOf(genericPickerItems),
            )
        )
        return ReadyState(setUp)
    }

}

private fun VisualizationSetUpUiModel.genericPickerTransitionItems(
    group: StringWrapper,
) = immutableListOf(
    GenericPickerItem(
        id = VertexTimeId.idString,
        title = StringResources(R.string.vertex_transition_time),
        iconResId = R.drawable.ic_transition,
        pickingDataType = DurationType(vertexTransitionTime),
        group = group,
    ),
    GenericPickerItem(
        id = ComparisonTimeId.idString,
        title = StringResources(R.string.comparison_transition_time),
        iconResId = R.drawable.ic_comparison,
        pickingDataType = DurationType(comparisonTransitionTime),
        group = group,
    )
)

//TODO better icons, this are not clear
private fun DrawColorsUiModel.genericPickerColorItems(
    group: StringWrapper
) = immutableListOf(

    GenericPickerItem(
        id = BackgroundColorId.idString,
        title = StringResources(R.string.set_up_picker_title_element_background_color),
        iconResId = R.drawable.ic_background_color,
        pickingDataType = ColorType(elementBackground),
        group = group,
    ),
    GenericPickerItem(
        id = ComparisonShapeColorId.idString,
        title = StringResources(R.string.set_up_picker_title_comparison_color),
        iconResId = R.drawable.ic_comparison,
        pickingDataType = ColorType(comparisonShapeColor),
        group = group,
    ),
    GenericPickerItem(
        id = ElementShapeColorId.idString,
        title = StringResources(R.string.set_up_picker_title_element_border_color),
        iconResId = R.drawable.ic_border,
        pickingDataType = ColorType(elementShapeColor),
        group = group,
    ),
    GenericPickerItem(
        id = ConnectionLineColorId.idString,
        title = StringResources(R.string.set_up_picker_title_connection_line_color),
        iconResId = R.drawable.ic_connection_line,
        pickingDataType = ColorType(connectionLineColor),
        group = group,
    ),
    GenericPickerItem(
        id = ArrowColorId.idString,
        title = StringResources(R.string.set_up_picker_title_arrow_color),
        iconResId = R.drawable.ic_arrow,
        pickingDataType = ColorType(connectionArrowColor),
        group = group,
    ),
    GenericPickerItem(
        id = TextColorId.idString,
        title = StringResources(R.string.set_up_picker_title_text_color),
        iconResId = R.drawable.ic_text_color,
        pickingDataType = ColorType(textColor),
        group = group,
    )
)

private fun DrawSizesUiModelDp.genericPickerSizesItems(
    group: StringWrapper,
) = immutableListOf(
    GenericPickerItem(
        id = CircleRadiusId.idString,
        title = StringResources(R.string.set_up_picker_title_circle_radius),
        iconResId = R.drawable.ic_circle_radius,
        pickingDataType = NumericType(circleRadius.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = SquareEdgeId.idString,
        title = StringResources(R.string.set_up_picker_title_square_edge),
        iconResId = R.drawable.ic_squre_edge_size,
        pickingDataType = NumericType(squareEdgeSize.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = ElementStrokeId.idString,
        title = StringResources(R.string.set_up_picker_title_element_border_stroke),
        iconResId = R.drawable.element_border_stroke_size,
        pickingDataType = NumericType(elementStroke.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = ConnectionLineStrokeId.idString,
        title = StringResources(R.string.set_up_picker_title_connection_line_stroke),
        iconResId = R.drawable.ic_connection_line,
        pickingDataType = NumericType(lineStroke.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = TextSizeId.idString,
        title = StringResources(R.string.set_up_picker_title_text_size),
        iconResId = R.drawable.ic_text_size,
        pickingDataType = NumericType(textSize.value, Units.Sp),
        group = group,
    ),
    GenericPickerItem(
        id = ArrowSize.idString,
        title = StringResources(R.string.set_up_picker_title_arrow_size),
        iconResId = R.drawable.ic_arrow,
        pickingDataType = NumericType(arrowSize.value, Units.Dp),
        group = group,
    ),
)
