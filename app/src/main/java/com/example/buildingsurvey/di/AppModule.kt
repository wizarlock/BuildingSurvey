package com.example.buildingsurvey.di

import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    fun provideRepository(repository: Repository): RepositoryInterface

    companion object {}
}