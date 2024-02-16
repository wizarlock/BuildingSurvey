package com.example.buildingsurvey.di

import android.content.Context
import androidx.work.WorkManager
import com.example.buildingsurvey.data.workmanager.CustomWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WorkerModule {
    companion object {
        @Singleton
        @Provides
        fun provideCustomWorkManager(workManager: WorkManager): CustomWorkManager {
            return CustomWorkManager(workManager)
        }

        @Singleton
        @Provides
        fun provideWorkManagerInstance(@ApplicationContext context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }
    }
}