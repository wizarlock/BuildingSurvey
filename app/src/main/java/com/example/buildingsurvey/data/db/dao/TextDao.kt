package com.example.buildingsurvey.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.TextDbEntity

@Dao
interface TextDao {
    @Insert
    fun insert(text: TextDbEntity)

    @Delete
    fun delete(text: TextDbEntity)

    @Query("SELECT * FROM TextDbEntity")
    fun getAllTexts(): List<TextDbEntity>
}