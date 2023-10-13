package com.arakim.datastructurevisualization.ui.visualizationEngine.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationEngine.compose.helper.drawConnection
import com.arakim.datastructurevisualization.ui.visualizationEngine.compose.helper.drawVertex
import com.arakim.datastructurevisualization.ui.visualizationEngine.compose.helper.toOffset
import com.arakim.datastructurevisualization.ui.visualizationEngine.compose.uiModel.DrawStyle
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model.VertexTransition
import kotlin.time.Duration

//TODO performance check
//TODO anime arrow
//TODO learn more about gestures and apply to improve managing canvas size
//TODO play with path effect when you finish
//TODO make use of it more declarative with .() and infix
//TODO add auto scale when elements can't fit into screen
@Composable
fun VisualizationEngine(
    presenter: VisualizationEnginePresenter,
    drawStyle: DrawStyle,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val currentVertexTransition = remember { mutableStateOf<Vertex?>(null) }
    val vertexTransitionAnim = remember {
        Animatable(initialValue = Offset.Zero, Offset.VectorConverter)
    }

    val shapeTransitionAnim = remember { Animatable(initialValue = Offset.Zero, Offset.VectorConverter) }

    suspend fun animateVertexToPosition(
        vertex: Vertex,
        startPosition: Offset,
        time: Duration,
    ) {
        currentVertexTransition.value = vertex
        vertexTransitionAnim.snapTo(startPosition)
        vertexTransitionAnim.animateTo(
            targetValue = vertex.element.position.toOffset(density),
            animationSpec = tween(time.inWholeMilliseconds.toInt()),
        )
    }

    suspend fun handleTransition(transition: VertexTransition) {
        when (transition) {
            is VertexTransition.EnterVertexTransition -> {

                transition.comparisons.forEach {
                    shapeTransitionAnim.animateTo(
                        targetValue = it.toOffset(density),
                        animationSpec = tween(transition.comparisonTransitionTime.inWholeMilliseconds.toInt()),
                    )
                }
                animateVertexToPosition(
                    vertex = transition.vertex,
                    startPosition = drawStyle.elementStartPosition.toOffset(density),
                    time = transition.vertexTransitionTime,
                )
            }

            is VertexTransition.MoveVertexTransition ->
                animateVertexToPosition(
                    vertex = transition.vertex,
                    startPosition = presenter.getPositionOf(transition.vertex.id)!!.toOffset(density),
                    time = transition.vertexTransitionTime,
                )
        }
    }

    LaunchedEffect(Unit) {
        presenter.currentVertexTransition.collect { transition ->
            if (transition == null) return@collect
            handleTransition(transition)
            currentVertexTransition.value = null
        }
    }

    val vertex = currentVertexTransition.value
    if (vertex != null && vertexTransitionAnim.isRunning) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawVertex(
                    vertex = vertex,
                    textMeasurer = textMeasurer,
                    position = vertexTransitionAnim.value,
                    drawStyle = drawStyle,
                )
            },
        )
    }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            presenter.vertexStateMap.forEach { (id, vertex) ->
                val connections = presenter.vertexConnectionsState[id]
                val vertexPosition = vertex.element.position.toOffset(density)

                // TODO O(n * a) improve
                // TODO make more declarative
                connections?.forEach { idOfConnection ->
                    val toPosition = if (idOfConnection == currentVertexTransition.value?.id)
                        vertexTransitionAnim.value
                    else
                        presenter.getPositionOf(idOfConnection)?.toOffset(density) ?: return@forEach

                    val from = if (id == currentVertexTransition.value?.id)
                        vertexTransitionAnim.value
                    else
                        vertexPosition

                    drawConnection(
                        from = from,
                        to = toPosition,
                        drawStyle = drawStyle,
                    )
                }

                if (id == currentVertexTransition.value?.id) return@forEach
                drawVertex(
                    vertex = vertex,
                    textMeasurer = textMeasurer,
                    position = vertexPosition,
                    drawStyle = drawStyle,
                )
            }
        },
    )

    AnimatedVisibility(
        visible = shapeTransitionAnim.isRunning,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = drawStyle.colors.animShapeColor,
                radius = drawStyle.sizes.circleRadius,
                center = shapeTransitionAnim.value,
                style = Stroke(drawStyle.sizes.elementStroke),
            )
        }
    }
}

private fun VisualizationEnginePresenter.getPositionOf(id: VertexId): DpOffset? =
    this.vertexStateMap[id]?.element?.position



