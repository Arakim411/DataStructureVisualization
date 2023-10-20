package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel.DrawStyle
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper.TransitionHandlerHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper.TransitionQueueHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationEnginePresenterSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElementShape.Circle

// TODO make more and better previews
@Composable
internal fun rememberVisualizationEngineState() = remember {
    VisualizationEnginePresenter(
        transitionQueueHelper = TransitionQueueHelper(),
        transitionHandlerHelper = TransitionHandlerHelper(),
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun VisualizationEnginePreview() {
    val state = rememberVisualizationEngineState().apply {
        initialize(
            setUp = DefaultVisualizationEnginePresenterSetUp,
        )
    }

    VisualizationEngine(
        presenter = state,
        drawStyle = remember { DrawStyle.Default },
    )


    var index = remember { 4 }
    LaunchedEffect(Unit) {

        state.createVertex(
            vertexInfo = VertexInfo(
                id = VertexId("1"),
                title = "1",
                position = DpOffset(100.dp, 100.dp),
                shape = Circle,
            )
        )

        state.createVertex(
            vertexInfo = VertexInfo(
                id = VertexId("2"),
                title = "2",
                position = DpOffset(100.dp, 200.dp),
                shape = Circle,
            )
        )

        state.createVertexWithEnterTransition(
            VertexInfo(
                id = VertexId("3"),
                title = "3",
                position = DpOffset(150.dp, 300.dp),
                shape = Circle,
            ),
            comparisons = immutableListOf(
                DpOffset(100.dp, 100.dp),
                DpOffset(100.dp, 200.dp),
                DpOffset(150.dp, 300.dp)
            )
        )
        state.createConnection(VertexId("1"), VertexId("3"))
    }

    Row {
        Button(onClick = {

            val id = VertexId(index.toString())
            state.createVertexWithEnterTransition(
                VertexInfo(
                    id = id,
                    title = index.toString(),
                    position = randomDp(),
                    shape = Circle,
                )
            )
            state.createConnection(VertexId("1"), id)
            state.createConnection(VertexId("2"), id)
            index++
        }) {
            Text(text = "add")
        }
        Button(
            onClick = {
                state.moveVertexGroup(
                    listOf(
                        VertexId("1") to randomDp(),
                        VertexId("2") to randomDp(),
                    )
                )
            },
        ) {
            Text(text = "move")
        }
    }

}

private fun randomDp(): DpOffset {
    val x = (0..400).random().dp
    val y = (0..500).random().dp
    return DpOffset(x, y)
}
