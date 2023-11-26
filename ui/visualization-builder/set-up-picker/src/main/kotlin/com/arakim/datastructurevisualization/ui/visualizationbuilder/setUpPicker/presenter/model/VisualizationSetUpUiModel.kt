package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import kotlin.time.Duration

@Immutable
data class VisualizationSetUpUiModel(
    val id: Int,
    val vertexTransitionTime: Duration,
    val comparisonTransitionTime: Duration,
    val enterTransStartPosition: DpOffset,
    val drawConfig: DrawConfigUiModel,
)

fun VisualizationSetUp.toUiModel(): VisualizationSetUpUiModel {
    return VisualizationSetUpUiModel(
        id = id,
        vertexTransitionTime = vertexTransitionTime,
        comparisonTransitionTime = vertexTransitionTime,
        enterTransStartPosition = enterTransStartPositionDp.toDpOffset(),
        drawConfig = drawConfig.toUiModel()

    )
}

fun VisualizationSetUpUiModel.toDomain(): VisualizationSetUp = VisualizationSetUp(
    id = id,
    vertexTransitionTime = vertexTransitionTime,
    comparisonTransitionTime = comparisonTransitionTime,
    enterTransStartPositionDp = enterTransStartPosition.toPair(),
    drawConfig = drawConfig.toDomain()
)

internal fun Pair<Int, Int>.toDpOffset() = DpOffset(first.dp, second.dp)
internal fun DpOffset.toPair() = Pair(x.value.toInt(), y.value.toInt())
