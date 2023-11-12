package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper.TransitionHandlerHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper.TransitionQueueHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape.Circle

// TODO make more and better previews
@Composable
internal fun rememberVisualizationEngineState() = remember {
    VisualizationCorePresenter(
        transitionQueueHelper = TransitionQueueHelper(),
        transitionHandlerHelper = TransitionHandlerHelper(),
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun VisualizationEnginePreview() {
    val state = rememberVisualizationEngineState()

    VisualizationCore(
        presenter = state,
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

        state.createVertex(
            VertexInfo(
                id = VertexId("3"),
                title = "3",
                position = DpOffset(150.dp, 300.dp),
                shape = Circle,
            ),
            )
        state.createVertex(
            VertexInfo(
                id = VertexId("4"),
                title = "4",
                position = DpOffset(150.dp, 350.dp),
                shape = Circle,
            ),
        )
        state.createConnection(VertexId("1"), VertexId("3"))
        state.createConnection(VertexId("1"), VertexId("2"))
        state.createConnection(VertexId("3"), VertexId("4"))
        state.createConnection(VertexId("4"), VertexId("1"))
    }

    Row {
        Button(onClick = {

            val id = VertexId(index.toString())
//            state.createVertexWithEnterTransition(
//                VertexInfo(
//                    id = id,
//                    title = index.toString(),
//                    position = randomDp(),
//                    shape = Circle,
//                )
//            )
            state.createConnection(VertexId("1"), id)
            state.createConnection(VertexId("2"), id)
            index++
        }) {
            Text(text = "add")
        }
        Button(
            onClick = {
                state.getAllOutGoingConnections(VertexId(("1")))

            },
        ) {
            Text(text = "get all connections")
        }
    }

}

private fun randomDp(): DpOffset {
    val x = (0..400).random().dp
    val y = (0..500).random().dp
    return DpOffset(x, y)
}
