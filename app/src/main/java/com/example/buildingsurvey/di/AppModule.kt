package com.example.buildingsurvey.di

import android.content.Context
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.Repository
import com.example.buildingsurvey.data.db.AppDatabase
import com.example.buildingsurvey.data.db.ProjectDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    fun provideRepository(repository: Repository): RepositoryInterface

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return AppDatabase.getDatabaseInstance(context)
        }

        @Singleton
        @Provides
        fun provideProjectDao(database: AppDatabase): ProjectDao {
            return database.getProjectDao()
        }
    }
}