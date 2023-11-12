package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.TextTransitionState.IdleState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.TextTransitionState.MovingState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexTransition.ActionTransition
import javax.inject.Inject

class CreateMoveTextTransitionHelper @Inject constructor() {

    operator fun invoke(
        from: VertexId,
        to: VertexId,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)?,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)?
    ): ActionTransition = ActionTransition(
        action = { corePresenter ->
            corePresenter.apply {
                changeTitleVisibility(from, false)
                changeTitleVisibility(to, false)
                val text = getVertex(from)?.element?.title ?: return@ActionTransition
                val fromPosition = getVertexFinalPosition(from) ?: return@ActionTransition
                val toPosition = getVertexFinalPosition(to) ?: return@ActionTransition

                val anim = Animatable(initialValue = fromPosition, DpOffset.VectorConverter)
                textTransitionState.value = MovingState(
                    text = text,
                    anim
                )
                anim.animateTo(
                    targetValue = toPosition,
                    tween(durationMillis = setUpState.value!!.vertexTransitionTime.inWholeMilliseconds.toInt())
                )
                changeTitle(to, text)
                textTransitionState.value = IdleState
                changeTitleVisibility(to, true)
            }
        },
        invokeBefore = invokeBefore,
        invokeAfter = invokeAfter,
    )

    private fun VisualizationCorePresenter.getVertexFinalPosition(
        id: VertexId
    ) = getVertex(id)?.element?.finalPosition
}