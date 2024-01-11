package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.VisualizationSetUpUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap.HashMapWrapper
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers.HashMapSerializer
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers.VisualizeBucketsInitializationHelper
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers.VisualizeValueAdd
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers.VisualizeValueDelete
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class HashMapVisualizationBuilder @Inject constructor(
    val visualizationBuilder: VisualizationBuilder,
    private val visualizeBucketsInitialization: VisualizeBucketsInitializationHelper,
    private val visualizeValueAdd: VisualizeValueAdd,
    private val visualizationValueDelete: VisualizeValueDelete,
    private val hashMapSerializer: HashMapSerializer,
) : HashMapWrapper(), DataStructureSerializer by hashMapSerializer {

    private lateinit var setUp: VisualizationSetUpUiModel

    init {
        visualizationBuilder.setOnVisualizationSetUpChanged {
            setUp = it
        }
    }

    override fun addValue(value: Int) {
        visualizationBuilder.addTransitionHelper.addActionTransition {
            super.addValue(value)
        }
    }

    override fun deleteValue(value: Int) {
        visualizationBuilder.addTransitionHelper.addActionTransition {
            super.deleteValue(value)
        }
    }

    fun initialize(
        dataStructureId: Int,
        coroutineScope: CoroutineScope,
        binaryHashMapJson: String? = null,
        onInitialized: () -> Unit,
        onHashMapCreated: () -> Unit,
        buckets: Int = DefaultBucketCount,
    ) {

        visualizationBuilder.initialize(
            dataStructureId = dataStructureId,
            coroutineScope = coroutineScope,
            onInitialized = {
                addListener(visualizeBucketsInitialization)
                addListener(visualizeValueAdd)
                addListener(visualizationValueDelete)

                hashMapSerializer.initialize(this@HashMapVisualizationBuilder)

                visualizationBuilder.visualizationCore.disableAnimations = true
                if (!binaryHashMapJson.isNullOrEmpty()) {
                    createFromJson(binaryHashMapJson)
                } else {
                    initializeBuckets(buckets)
                }

                onInitialized()
                coroutineScope.yielded {
                    //TODO again hack needs to introduce mechanism to wait until all animations are finished
                    delay(1000)
                    visualizationBuilder.visualizationCore.disableAnimations = false
                    waitUntilHashMapIsCreated(onHashMapCreated)
                }
            },
        )
    }

    private suspend fun waitUntilHashMapIsCreated(onCreated: () -> Unit) {
        // TODO this is hack and later should be implemented correctly
        visualizationBuilder.visualizationCore.apply {
            while (hasQueuedTransitions) {
                delay(500)
            }
        }
        onCreated()
    }
}

fun Any.toVertexId() = VertexId(this.toString())

private const val DefaultBucketCount = 6
