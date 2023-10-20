package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.helper.drawConnection
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.helper.drawVisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.helper.toOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel.DrawStyle
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState.ComparingState

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
    TransformableBox {
        VisualizationEngineView(
            presenter = presenter,
            drawStyle = drawStyle,
        )
    }
}

@Composable
private fun VisualizationEngineView(
    presenter: VisualizationEnginePresenter,
    drawStyle: DrawStyle,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val shapeTransitionState = presenter.comparisonState.value

    val lifecycleOwner = LocalLifecycleOwner.current

    val coroutineScope = rememberCoroutineScope()
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
            presenter.vertexStateMap.forEach { (id, vertex) ->
                if (!vertex.element.isVisible && !vertex.element.position.isRunning) return@forEach

                val connections = presenter.vertexConnectionsState[id]
                val vertexPosition = vertex.element.position.value.toOffset(density)

                connections?.forEach { idOfConnection ->
                    val vertexToConnect = presenter.vertexStateMap[idOfConnection]
                    if (vertexToConnect?.element?.isVisible == false && !vertexToConnect.element.position.isRunning) return@forEach
                    val toPosition = vertexToConnect?.element?.position?.value?.toOffset(density)
                        ?: return@forEach

                    drawConnection(
                        from = vertexPosition,
                        to = toPosition,
                        drawStyle = drawStyle,
                    )
                }
                drawVisualizationElement(
                    element = vertex.element,
                    textMeasurer = textMeasurer,
                    center = vertexPosition,
                    drawStyle = drawStyle,
                )
            }
        },
    )

    Crossfade(shapeTransitionState, label = "") { stateValue ->
        if (stateValue is ComparingState) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = drawStyle.colors.animShapeColor,
                    radius = drawStyle.sizes.circleRadius,
                    center = stateValue.position.value.toOffset(density),
                    style = Stroke(drawStyle.sizes.elementStroke),
                )
            }
        }
    }
}



