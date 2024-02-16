package com.example.buildingsurvey.data.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.WorkerFactory
import com.example.buildingsurvey.data.RepositoryInterface
import javax.inject.Inject

class WorkerFactory @Inject constructor(
    private val repository: RepositoryInterface
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return NetworkUnavailableWorker(repository, appContext, workerParameters)
    }
}
