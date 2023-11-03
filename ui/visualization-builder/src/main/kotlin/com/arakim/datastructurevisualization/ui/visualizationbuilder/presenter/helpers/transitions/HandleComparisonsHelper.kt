package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape
import javax.inject.Inject

class HandleComparisonsHelper @Inject constructor() {

    suspend fun VisualizationCorePresenter.invoke(
        comparisons: List<DpOffset>,
        shape: VisualizationElementShape,
    ) {
        comparisons.firstOrNull()?.also { firstPosition ->
            val anim = Animatable(initialValue = firstPosition, DpOffset.VectorConverter)

            comparisonState.value = ComparisonState.ComparingState(anim, shape)
            comparisons.drop(1).forEach { position ->
                anim.animateTo(
                    position,
                    tween(durationMillis = setUp.comparisonTransitionTime.inWholeMilliseconds.toInt())
                )
            }
            comparisonState.value = ComparisonState.IdleState
        }
    }

}