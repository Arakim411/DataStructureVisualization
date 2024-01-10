package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.VisualizationSetUpUiModel
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers.HashMapSerializer
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class HashMapVisualizationBuilder @Inject constructor(
    val visualizationBuilder: VisualizationBuilder,
    private val hashMapSerializer: HashMapSerializer,
) : DataStructureSerializer by hashMapSerializer {

    private lateinit var setUp: VisualizationSetUpUiModel

    init {
        visualizationBuilder.setOnVisualizationSetUpChanged {
            setUp = it
        }
    }

    fun initialize(
        dataStructureId: Int,
        coroutineScope: CoroutineScope,
        binaryHashMapJson: String? = null,
        onInitialized: () -> Unit,
        onHashMapCreated: () -> Unit,
    ) {
        //TODO handle recreating from binaryHashMapJson
        visualizationBuilder.initialize(
            dataStructureId = dataStructureId,
            coroutineScope = coroutineScope,
            onInitialized = {
                onInitialized()
                coroutineScope.yielded {
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