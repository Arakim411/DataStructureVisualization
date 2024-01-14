package com.arakim.datastructurevisualization.data.repository.dataStructure

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Operation.State
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastructurevisualization.kotlinutil.getOrNull
import com.arakim.datastructurevisualization.kotlinutil.onSuccess
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.DataStructureLocalDataSource
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureId
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class DataStructureRepositoryImpl @Inject constructor(
    private val localDataSource: DataStructureLocalDataSource,
    @ApplicationContext private val context: Context,
) : DataStructureRepository {

    override suspend fun createDataStructure(
        name: String,
        type: DataStructureType,
    ): TypedResult<DataStructureId, CommonError> = localDataSource.createDataStructure(
        name = name,
        dataSourceType = type,
    )

    override suspend fun updateDataStructure(dataStructure: DataStructure): TypedResult<Unit, CommonError> =
        localDataSource.updateDataStructure(dataStructure)


    override suspend fun getDataStructure(id: Int): TypedResult<DataStructure, CommonError> =
        localDataSource.getDataStructure(id)

    override suspend fun deleteDataStructure(id: Int): TypedResult<Unit, CommonError> {
        return localDataSource.deleteDataStructure(id)
    }

    override suspend fun startDataStructureDeletionProcess(id: Int): TypedResult<Unit, CommonError> {
        val deletionDate = getDeletionTimeUtc()
        val result = localDataSource.setDeletionTime(id, deletionDate)
        result.onSuccess {
            startDeletionProcess(id, deletionDate)
        }

        return result
    }

    override suspend fun stopDeletionProcess(id: Int): TypedResult<Unit, CommonError> {
        localDataSource.removeDeletionTime(id).getOrNull() ?: return TypedResult.failure(CommonError)
        return stopDeletionWork(id)
    }

    override fun listenForDataStructuresUpdate(): Flow<TypedResult<List<DataStructure>, CommonError>> =
        localDataSource.listenForDataStructuresUpdate()


    private fun getDeletionTimeUtc(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, DeletionDelayHours)
        return calendar.timeInMillis
    }

    private fun startDeletionProcess(id: Int, deletionDate: Long) {
        val delay = deletionDate - System.currentTimeMillis()
        val data = workDataOf(DeleteDataStructureWorker.DATA_STRUCTURE_ID to id)

        val workRequest = OneTimeWorkRequestBuilder<DeleteDataStructureWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                id.toString(),
                androidx.work.ExistingWorkPolicy.KEEP,
                workRequest.build(),
            )
    }

    private fun stopDeletionWork(id: Int): TypedResult<Unit, CommonError> {
        WorkManager.getInstance(context).cancelUniqueWork(id.toString())
        val result = WorkManager.getInstance(context).cancelUniqueWork(id.toString()).result.get()

        return if (result is State.SUCCESS) {
            TypedResult.success(Unit)
        } else {
            TypedResult.failure(CommonError)
        }
    }


    companion object {
        private const val DeletionDelayHours = 1
    }
}