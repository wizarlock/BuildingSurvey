package com.example.buildingsurvey.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.buildingsurvey.data.RepositoryInterface
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NetworkUnavailableWorker @AssistedInject constructor(
    @Assisted private val repository: RepositoryInterface,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        repository.loadDataFromDB()
        return Result.success()
    }
}