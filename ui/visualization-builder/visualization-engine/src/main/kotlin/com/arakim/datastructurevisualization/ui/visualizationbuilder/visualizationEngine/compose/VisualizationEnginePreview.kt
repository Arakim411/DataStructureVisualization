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
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel.DrawStyleDefault
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenterImpl
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultPresenterSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            setUp = DefaultPresenterSetUp,
        )
    }

    VisualizationEngine(
        presenter = state,
        drawStyle = remember { DrawStyleDefault },
    )


    var lastY = remember { 300.dp }
    LaunchedEffect(Unit) {
        state.createVertexWithEnterTransition(
            Vertex(
                id = VertexId("1"),
                element = VisualizationElement(
                    "1",
                    DpOffset(200.dp, 200.dp)
                ),
            ),
        )
        state.createVertexWithEnterTransition(
            Vertex(
                id = VertexId("5"),
                element = VisualizationElement(
                    "5",
                    DpOffset(200.dp, 300.dp)
                ),
            ),
        )

        state.createVertexWithEnterTransition(
            vertex = Vertex(
                id = VertexId("30"),
                element = VisualizationElement(
                    "5",
                    DpOffset(300.dp, 400.dp)
                ),
            ),
        )

        state.createConnection(VertexId("1"), VertexId("5"))
        coroutineScope.launch(Dispatchers.IO) {
            (0..20).forEach {
                state.createVertex(
                    Vertex(
                        id = VertexId("XD$it"),
                        element = VisualizationElement(
                            "$it",
                            randomDp()
                        ),
                    )

                )
                state.createConnection(
                    VertexId("XD$it"),
                    VertexId("1")
                )
            }
        }
    }

    Row {
        Button(onClick = {
            state.createVertex(
                Vertex(
                    id = VertexId("7$lastY"),
                    element = VisualizationElement(
                        "5",
                        randomDp(),
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
