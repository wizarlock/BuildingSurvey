package com.example.buildingsurvey.data.datastore

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.protoDataStore by dataStore("settings.json", PreferencesSerializer)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
    private val preferencesDataStore = appContext.protoDataStore
    val userPreferences = preferencesDataStore.data

    suspend fun updatePhotoNum(num: Int) {
        preferencesDataStore.updateData { userPreferences ->
            userPreferences.copy(photoNum = num)
        }
    }

    suspend fun updateAudioNum(num: Int) {
        preferencesDataStore.updateData { userPreferences ->
            userPreferences.copy(audioNum = num)
        }
    }
}