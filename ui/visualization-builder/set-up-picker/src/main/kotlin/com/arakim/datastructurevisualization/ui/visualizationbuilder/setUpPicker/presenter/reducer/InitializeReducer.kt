package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer

import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.InitializedAction
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
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.InitializeAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerState.ReadyState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.*
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.ComparisonTimeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer.VisualizationSetUpItemId.VertexTimeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setuppicker.R
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DrawColors
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DrawSizes
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationSetUp
import javax.inject.Inject

class InitializeReducer @Inject constructor() : StateReducer<State, Action, InitializeAction>() {

    private lateinit var genericPickerPresenter: GenericPickerPresenter

    fun setGenericPickerPresenter(genericPickerPresenter: GenericPickerPresenter) {
        this.genericPickerPresenter = genericPickerPresenter
    }

    override fun State.reduce(action: InitializeAction): State {

        val transitionGroup = StringResources(R.string.set_up_picker_group_transition)
        val sizeGroup = StringResources(R.string.set_up_picker_group_sizes)
        val colorGroup = StringResources(R.string.set_up_picker_group_color)

        val defaultSetUp = DefaultVisualizationSetUp

        val genericPickerItems = mutableListOf<GenericPickerItem<*>>().apply {
            addAll(defaultSetUp.genericPickerTransitionItems(transitionGroup))
            addAll(defaultSetUp.drawConfig.sizes.genericPickerSizesItems(sizeGroup))
            addAll(defaultSetUp.drawConfig.colors.genericPickerColorItems(colorGroup))
        }.mapToImmutable { it }

        genericPickerPresenter.onAction(
            InitializedAction(
                allItems = genericPickerItems,
                floatingModalItems = immutableListOf(),
            )
        )

        return ReadyState(defaultSetUp)
    }

}

private fun VisualizationSetUp.genericPickerTransitionItems(
    group: StringWrapper,
) = immutableListOf(
    GenericPickerItem(
        id = VertexTimeId.idString,
        title = StringResources(R.string.vertex_transition_time),
        iconResId = 0,
        pickingDataType = DurationType(vertexTransitionTime),
        group = group,
    ),
    GenericPickerItem(
        id = ComparisonTimeId.idString,
        title = StringResources(R.string.comparison_transition_time),
        iconResId = 0,
        pickingDataType = DurationType(comparisonTransitionTime),
        group = group,
    )
)

//TODO use correct icon„
val testIcon = R.drawable.test_icon
private fun DrawColors.genericPickerColorItems(
    group: StringWrapper
) = immutableListOf(

    GenericPickerItem(
        id = BackgroundColorId.idString,
        title = StringResources(R.string.set_up_picker_title_element_background_color),
        iconResId = testIcon,
        pickingDataType = ColorType(elementBackground),
        group = group,
    ),
    GenericPickerItem(
        id = ShapeColorId.idString,
        title = StringResources(R.string.set_up_picker_title_element_shape_color),
        iconResId = testIcon,
        pickingDataType = ColorType(animShapeColor),
        group = group,
    ),
    GenericPickerItem(
        id = LineColorId.idString,
        title = StringResources(R.string.set_up_picker_title_element_line_color),
        iconResId = testIcon,
        pickingDataType = ColorType(elementLineColor),
        group = group,
    ),
    GenericPickerItem(
        id = ConnectionLineColorId.idString,
        title = StringResources(R.string.set_up_picker_title_connection_line_color),
        iconResId = testIcon,
        pickingDataType = ColorType(connectionLineColor),
        group = group,
    ),
    GenericPickerItem(
        id = ArrowColorId.idString,
        title = StringResources(R.string.set_up_picker_title_arrow_color),
        iconResId = testIcon,
        pickingDataType = ColorType(connectionArrowColor),
        group = group,
    ),
    GenericPickerItem(
        id = TextColorId.idString,
        title = StringResources(R.string.set_up_picker_title_text_color),
        iconResId = testIcon,
        pickingDataType = ColorType(textColor),
        group = group,
    )
)

private fun DrawSizes.genericPickerSizesItems(
    group: StringWrapper,
) = immutableListOf(
    GenericPickerItem(
        id = CircleRadiusId.idString,
        title = StringResources(R.string.set_up_picker_title_circle_radius),
        iconResId = testIcon,
        pickingDataType = NumericType(circleRadius.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = SquareEdgeId.idString,
        title = StringResources(R.string.set_up_picker_title_square_edge),
        iconResId = testIcon,
        pickingDataType = NumericType(squareEdgeSize.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = ElementStrokeId.idString,
        title = StringResources(R.string.set_up_picker_title_element_stroke),
        iconResId = testIcon,
        pickingDataType = NumericType(elementStroke.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = LineStrokeId.idString,
        title = StringResources(R.string.set_up_picker_title_line_stroke),
        iconResId = testIcon,
        pickingDataType = NumericType(lineStroke.value, Units.Dp),
        group = group,
    ),
    GenericPickerItem(
        id = TextSizeId.idString,
        title = StringResources(R.string.set_up_picker_title_text_size),
        iconResId = testIcon,
        pickingDataType = NumericType(textSize.value, Units.Sp),
        group = group,
    ),
    GenericPickerItem(
        id = ArrowSize.idString,
        title = StringResources(R.string.set_up_picker_title_arrow_size),
        iconResId = testIcon,
        pickingDataType = NumericType(arrowSize.value, Units.Dp),
        group = group,
    ),
)
