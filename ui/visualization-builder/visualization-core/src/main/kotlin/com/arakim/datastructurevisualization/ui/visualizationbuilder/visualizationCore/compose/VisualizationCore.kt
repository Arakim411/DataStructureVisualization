package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import com.arakim.datastructurevisualization.ui.util.views.TransformableBox
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.toDrawSizesPx
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.helper.drawConnection
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.helper.drawElementTitle
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.helper.drawVisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.helper.toOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.ComparisonState.ComparingState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.TextTransitionState.MovingState

//TODO performance check
//TODO anime arrow
//TODO play with path effect when you finish
//TODO make use of it more declarative with .() and infix
//TODO add auto scale when elements can't fit into screen


@Composable
fun VisualizationCore(
    presenter: VisualizationCorePresenter,
) {
    TransformableBox {
        VisualizationCoreView(
            presenter = presenter,
        )
    }
}

@Composable
private fun VisualizationCoreView(
    presenter: VisualizationCorePresenter,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val shapeTransitionState = presenter.comparisonState.value
    val textTransition = presenter.textTransitionState.value

    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val drawConfig = remember(presenter.setUpState.value!!.drawConfig) {
        presenter.setUpState.value!!.drawConfig
    }
    val drawSizesPx = remember(drawConfig) { drawConfig.sizes.toDrawSizesPx(density) }

    DisposableEffect(Unit) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                ON_RESUME -> presenter.onViewReady(coroutineScope)
                ON_PAUSE -> presenter.onViewNotReady()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            presenter.onViewNotReady()
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            presenter.vertexStateMap.forEach vertexForEach@{ (id, vertex) ->
                if (!vertex.element.isVisible && !vertex.element.position.isRunning) return@vertexForEach

                val connections = presenter.vertexConnectionsState[id]
                val vertexPosition = vertex.element.position.value.toOffset(density)

                connections?.forEach connectionsForEach@{ idOfConnection ->
                    val vertexToConnect = presenter.vertexStateMap[idOfConnection]
                    if (vertexToConnect?.element?.showIncomingConnections == false) return@connectionsForEach
                    if (vertexToConnect?.element?.isVisible == false && !vertexToConnect.element.position.isRunning) return@connectionsForEach
                    val toPosition =
                        vertexToConnect?.element?.position?.value?.toOffset(density)
                            ?: return@connectionsForEach

                    drawConnection(
                        from = vertexPosition,
                        to = toPosition,
                        drawConfig = drawConfig,
                        circleRadius = drawSizesPx.circleRadius,
                        lineStroke = drawSizesPx.lineStroke,
                        arrowSize = drawSizesPx.arrowSize,
                    )
                }
                drawVisualizationElement(
                    element = vertex.element,
                    textMeasurer = textMeasurer,
                    center = vertexPosition,
                    colors = drawConfig.colors,
                    circleRadius = drawSizesPx.circleRadius,
                    elementStroke = drawSizesPx.elementStroke,
                    squareEdgeSize = drawSizesPx.squareEdgeSize,
                    fontSize = drawSizesPx.textSize,

                )
            }
        },
    )
    Crossfade(textTransition, label = "") { stateValue ->
        if (stateValue is MovingState) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawElementTitle(
                    title = stateValue.text,
                    center = stateValue.position.value.toOffset(density),
                    colors = drawConfig.colors,
                    fontSize = drawSizesPx.textSize,
                    textMeasurer = textMeasurer,
                )
            }
        }
    }

    Crossfade(shapeTransitionState, label = "") { stateValue ->
        if (stateValue is ComparingState) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = drawConfig.colors.comparisonShapeColor,
                    radius = drawSizesPx.circleRadius,
                    center = stateValue.position.value.toOffset(density),
                    style = Stroke(drawSizesPx.elementStroke),
                )
            }
        }
    }
}



