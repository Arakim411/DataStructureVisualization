package com.arakim.datastructurevisualization.ui.screens.choosedatastructure.presenter

import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.CreateDataStructureReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.DeleteDataStructureReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.InitializeReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.UpdateDataStructureIsFavoriteReducer
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.CoroutineScope

class ChooseDataStructurePresenterMockHelper {

    val initializeReducer = mockk<InitializeReducer>()
    val createdDataStructureReducer = mockk<CreateDataStructureReducer>()
    val deleteDataStructureReducer = mockk<DeleteDataStructureReducer>()
    val updateIsFavoriteReducer = mockk<UpdateDataStructureIsFavoriteReducer>()

    val reducers = listOf(
        initializeReducer,
        createdDataStructureReducer,
        deleteDataStructureReducer,
        updateIsFavoriteReducer,
    )


    fun mockReducersInitialization(coroutineScope: CoroutineScope) {
        every { initializeReducer.initialize(coroutineScope, any(), any(), any()) } just runs
        every { createdDataStructureReducer.initialize(coroutineScope, any(), any(), any()) } just runs
        every { deleteDataStructureReducer.initialize(coroutineScope, any(), any()) } just runs
        every { updateIsFavoriteReducer.initialize(coroutineScope, any(), any()) } just runs
    }

}