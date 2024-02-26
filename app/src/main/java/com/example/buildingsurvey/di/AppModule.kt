package com.example.buildingsurvey.di

import android.content.Context
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.Repository
import com.example.buildingsurvey.data.db.AppDatabase
import com.example.buildingsurvey.data.db.dao.AudioDao
import com.example.buildingsurvey.data.db.dao.DefectDao
import com.example.buildingsurvey.data.db.dao.DefectPointDao
import com.example.buildingsurvey.data.db.dao.DrawingDao
import com.example.buildingsurvey.data.db.dao.LabelDao
import com.example.buildingsurvey.data.db.dao.ProjectDao
import com.example.buildingsurvey.data.db.dao.TextDao
import com.example.buildingsurvey.data.db.dao.TypeOfDefectDao
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

        @Singleton
        @Provides
        fun provideDrawingDao(database: AppDatabase): DrawingDao {
            return database.getDrawingDao()
        }

        @Singleton
        @Provides
        fun provideAudioDao(database: AppDatabase): AudioDao {
            return database.getAudioDao()
        }

        @Singleton
        @Provides
        fun provideLabelDao(database: AppDatabase): LabelDao {
            return database.getLabelDao()
        }

        @Singleton
        @Provides
        fun provideTypeOfDefectDao(database: AppDatabase): TypeOfDefectDao {
            return database.getTypeOfDefectDao()
        }

        @Singleton
        @Provides
        fun provideDefectDao(database: AppDatabase): DefectDao {
            return database.getDefectDao()
        }

        @Singleton
        @Provides
        fun provideDefectPointDao(database: AppDatabase): DefectPointDao {
            return database.getDefectPointDao()
        }

        @Singleton
        @Provides
        fun provideTextDao(database: AppDatabase): TextDao {
            return database.getTextDao()
        }
    }
}