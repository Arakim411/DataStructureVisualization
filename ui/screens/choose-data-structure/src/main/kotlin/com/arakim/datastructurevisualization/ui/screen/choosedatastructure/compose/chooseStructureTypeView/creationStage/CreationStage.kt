package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage

import androidx.compose.runtime.saveable.mapSaver
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.ChooseNameState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.ChooseTypeStage
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.DataStructureSavableTypes.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.DataStructureSavableTypes.HashMap
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.DataStructureSavableTypes.LinkedList
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.StageKey
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.StageValueChooseName
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.StageValueChooseType
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.Companion.TypeKey
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel

internal sealed interface CreationStage {

    object ChooseTypeStage : CreationStage
    data class ChooseNameState(val type: DataStructureTypeUiModel) : CreationStage

    companion object {
        val Saver = CreatingStageSaver

        internal const val StageKey = "stage"
        internal const val TypeKey = "type"

        internal const val StageValueChooseType = "choose_type"
        internal const val StageValueChooseName = "choose_name"

        internal object DataStructureSavableTypes {
            const val BinarySearchTree = "binary_search_tree"
            const val HashMap = "hash_map"
            const val LinkedList = "linked_list"
        }
    }
}

private val CreatingStageSaver = mapSaver(
    save = {
        val typeValue = when (it) {
            is ChooseNameState -> it.type.getSavableValue()
            else -> null
        }
        mapOf(
            StageKey to it.getStageSavableValue(),
            TypeKey to typeValue
        )
    },
    restore = {
        it.toCreatingStage()
    }
)

private fun CreationStage.getStageSavableValue(): String = when (this) {
    ChooseTypeStage -> StageValueChooseType
    is ChooseNameState -> StageValueChooseName
}

private fun DataStructureTypeUiModel.getSavableValue(): String = when (this) {
    DataStructureTypeUiModel.BinarySearchTree -> BinarySearchTree
    DataStructureTypeUiModel.HashMap -> HashMap
    DataStructureTypeUiModel.LinkedList -> LinkedList
}

private fun Map<String, Any?>.toCreatingStage(): CreationStage = when (this[StageKey]) {
    StageValueChooseType -> ChooseTypeStage
    StageValueChooseName -> ChooseNameState(this[TypeKey]!!.toDataStructureTypeUiModel())
    else -> throw IllegalArgumentException("Unknown stage ${this[StageKey]}")
}

private fun Any.toDataStructureTypeUiModel(): DataStructureTypeUiModel = when (this) {
    BinarySearchTree -> DataStructureTypeUiModel.BinarySearchTree
    HashMap -> DataStructureTypeUiModel.HashMap
    LinkedList -> DataStructureTypeUiModel.LinkedList
    else -> throw IllegalArgumentException("Unknown type $this")

}
