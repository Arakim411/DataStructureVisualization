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
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel.DrawStyle
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenterImpl
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationEnginePresenterSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElementShape

// TODO make more and better previews
@Composable
internal fun rememberVisualizationEngineState() = remember {
    VisualizationEnginePresenterImpl()
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun VisualizationEnginePreview() {
    val coroutineScope = rememberCoroutineScope()
    val state = rememberVisualizationEngineState().apply {
        initialize(
            coroutineScope = coroutineScope,
            setUp = DefaultVisualizationEnginePresenterSetUp,
        )
    }

    VisualizationEngine(
        presenter = state,
        drawStyle = remember { DrawStyle.Default },
    )


    var lastY = remember { 300.dp }
    LaunchedEffect(Unit) {
        state.createVertexWithEnterTransition(
            Vertex(
                id = VertexId("1"),
                element = VisualizationElement(
                    "1",
                    DpOffset(200.dp, 200.dp),
                    shape = VisualizationElementShape.Square,
                ),
            ),
        )
        state.createVertexWithEnterTransition(
            Vertex(
                id = VertexId("5"),
                element = VisualizationElement(
                    "5",
                    DpOffset(200.dp, 300.dp),
                    shape = VisualizationElementShape.Circle,
                ),
            ),
        )

        state.createVertexWithEnterTransition(
            vertex = Vertex(
                id = VertexId("30"),
                element = VisualizationElement(
                    "5",
                    DpOffset(300.dp, 400.dp),
                    shape = VisualizationElementShape.Square,
                ),
            ),
        )

        state.createConnection(VertexId("1"), VertexId("5"))
    }

    Row {
        Button(onClick = {
            state.createVertex(
                Vertex(
                    id = VertexId("7$lastY"),
                    element = VisualizationElement(
                        "5",
                        randomDp(),
                        shape = VisualizationElementShape.Square,
                    ),
                ),
            )
            state.createConnection(VertexId("1"), VertexId("7$lastY"))
            lastY += 50.dp
        }) {
            Text(text = "add")
        }
        Button(onClick = {
            state.moveVertex(VertexId("1"), randomDp())
        }) {
            Text(text = "move")
        }
    }

}

private fun randomDp(): DpOffset {
    val x = (0..400).random().dp
    val y = (0..500).random().dp
    return DpOffset(x, y)
}
