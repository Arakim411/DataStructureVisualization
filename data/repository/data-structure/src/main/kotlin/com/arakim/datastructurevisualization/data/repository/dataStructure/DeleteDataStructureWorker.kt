package com.arakim.datastructurevisualization.data.repository.dataStructure

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arakim.datastructurevisualization.kotlinutil.isSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class DeleteDataStructureWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataStructureRepository: DataStructureRepositoryImpl,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val id = inputData.getInt(DATA_STRUCTURE_ID, -1)
        if (id == -1) {
            return Result.failure()
        }

        val isSuccess = dataStructureRepository.deleteDataStructure(id).isSuccess()

        return if (isSuccess) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    companion object {
        const val DATA_STRUCTURE_ID = "DATA_STRUCTURE_ID"
    }

}