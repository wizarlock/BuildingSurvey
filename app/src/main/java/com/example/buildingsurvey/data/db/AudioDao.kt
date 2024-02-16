package com.example.buildingsurvey.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.AudioDbEntity

@Dao
interface AudioDao {
    @Insert
    fun insert(audio: AudioDbEntity)

    @Delete
    fun delete(audio: AudioDbEntity)

    @Query("SELECT * FROM AudioDbEntity")
    fun getAllAudio(): List<AudioDbEntity>
}