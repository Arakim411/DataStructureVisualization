package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer

import android.content.res.Resources.NotFoundException
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.Action
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.State
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerAction.VisualizationSetUpUpdatedAction
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerState.ReadyState
import javax.inject.Inject

private typealias ItemId = VisualizationSetUpItemId

class HandleVisualizationSetUpUpdated @Inject constructor(
) : StateReducer<State, Action, VisualizationSetUpUpdatedAction>() {

    override fun State.reduce(action: VisualizationSetUpUpdatedAction): State = when (this) {
        is ReadyState -> reduceVisualizationSetUpUpdate(action.item)
        else -> logInvalidState()
    }

    @Suppress("UNCHECKED_CAST")
    private fun ReadyState.reduceVisualizationSetUpUpdate(item: GenericPickerItem<*>): State =
        when (item.id) {
            ItemId.VertexTimeId.idString -> updateVertexTime(item as GenericPickerItem<DurationType>)
            ItemId.ComparisonTimeId.idString -> updateComparisonTime(item as GenericPickerItem<DurationType>)
            ItemId.BackgroundColorId.idString -> updateBackgroundColor(item as GenericPickerItem<ColorType>)
            ItemId.ShapeColorId.idString -> updateShapeColor(item as GenericPickerItem<ColorType>)
            ItemId.LineColorId.idString -> updateLineColor(item as GenericPickerItem<ColorType>)
            ItemId.ConnectionLineColorId.idString -> updateConnectionLineColor(item as GenericPickerItem<ColorType>)
            ItemId.ArrowColorId.idString -> updateArrowColor(item as GenericPickerItem<ColorType>)
            ItemId.TextColorId.idString -> updateTextColor(item as GenericPickerItem<ColorType>)
            ItemId.CircleRadiusId.idString -> updateCircleRadius(item as GenericPickerItem<NumericType>)
            ItemId.SquareEdgeId.idString -> updateSquareEdge(item as GenericPickerItem<NumericType>)
            ItemId.ElementStrokeId.idString -> updateElementStroke(item as GenericPickerItem<NumericType>)
            ItemId.LineStrokeId.idString -> updateLineStroke(item as GenericPickerItem<NumericType>)
            ItemId.TextSizeId.idString -> updateTextSize(item as GenericPickerItem<NumericType>)
            ItemId.ArrowSize.idString -> updateArrowSize(item as GenericPickerItem<NumericType>)
            else -> throw NotFoundException("Item with id: ${item.id} not found")
        }

    private fun ReadyState.updateVertexTime(item: GenericPickerItem<DurationType>): State {
        val newSetUp = setUp.copy(vertexTransitionTime = item.pickingDataType.value)

        return copy(setUp = newSetUp)
    }


    private fun ReadyState.updateComparisonTime(item: GenericPickerItem<DurationType>): State {
        val newSetUp = setUp.copy(comparisonTransitionTime = item.pickingDataType.value)

        return copy(setUp = newSetUp)
    }


    private fun ReadyState.updateBackgroundColor(item: GenericPickerItem<ColorType>): State {
        val newColors = setUp.drawConfig.colors.copy(elementBackground = item.pickingDataType.value)
        val newDrawConfig = setUp.drawConfig.copy(colors = newColors)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }


    private fun ReadyState.updateShapeColor(item: GenericPickerItem<ColorType>): State {
        val newColors = setUp.drawConfig.colors.copy(animShapeColor = item.pickingDataType.value)
        val newDrawConfig = setUp.drawConfig.copy(colors = newColors)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)

    }

    private fun ReadyState.updateLineColor(item: GenericPickerItem<ColorType>): State {
        val newColors = setUp.drawConfig.colors.copy(elementLineColor = item.pickingDataType.value)
        val newDrawConfig = setUp.drawConfig.copy(colors = newColors)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateConnectionLineColor(item: GenericPickerItem<ColorType>): State {
        val newColors = setUp.drawConfig.colors.copy(connectionLineColor = item.pickingDataType.value)
        val newDrawConfig = setUp.drawConfig.copy(colors = newColors)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateArrowColor(item: GenericPickerItem<ColorType>): State {
        val newColors = setUp.drawConfig.colors.copy(connectionArrowColor = item.pickingDataType.value)
        val newDrawConfig = setUp.drawConfig.copy(colors = newColors)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateTextColor(item: GenericPickerItem<ColorType>): State {
        val newColors = setUp.drawConfig.colors.copy(textColor = item.pickingDataType.value)
        val newDrawConfig = setUp.drawConfig.copy(colors = newColors)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateCircleRadius(item: GenericPickerItem<NumericType>): State {
        val newSizes = setUp.drawConfig.sizes.copy(circleRadius = item.pickingDataType.value.toFloat().dp)
        val newDrawConfig = setUp.drawConfig.copy(sizes = newSizes)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateSquareEdge(item: GenericPickerItem<NumericType>): State {
        val newSizes = setUp.drawConfig.sizes.copy(squareEdgeSize = item.pickingDataType.value.toFloat().dp)
        val newDrawConfig = setUp.drawConfig.copy(sizes = newSizes)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateElementStroke(item: GenericPickerItem<NumericType>): State {
        val newSizes = setUp.drawConfig.sizes.copy(elementStroke = item.pickingDataType.value.toFloat().dp)
        val newDrawConfig = setUp.drawConfig.copy(sizes = newSizes)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateLineStroke(item: GenericPickerItem<NumericType>): State {
        val newSizes = setUp.drawConfig.sizes.copy(lineStroke = item.pickingDataType.value.toFloat().dp)
        val newDrawConfig = setUp.drawConfig.copy(sizes = newSizes)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateTextSize(item: GenericPickerItem<NumericType>): State {
        val newSizes = setUp.drawConfig.sizes.copy(textSize = item.pickingDataType.value.toFloat().sp)
        val newDrawConfig = setUp.drawConfig.copy(sizes = newSizes)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }

    private fun ReadyState.updateArrowSize(item: GenericPickerItem<NumericType>): State {
        val newSizes = setUp.drawConfig.sizes.copy(arrowSize = item.pickingDataType.value.toFloat().dp)
        val newDrawConfig = setUp.drawConfig.copy(sizes = newSizes)
        val newSetUp = setUp.copy(drawConfig = newDrawConfig)

        return copy(setUp = newSetUp)
    }


}