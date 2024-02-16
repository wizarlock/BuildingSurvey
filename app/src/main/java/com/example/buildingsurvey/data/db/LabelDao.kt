package com.example.buildingsurvey.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.LabelDbEntity

@Dao
interface LabelDao {
    @Insert
    fun insert(label: LabelDbEntity)

    @Delete
    fun delete(label: LabelDbEntity)

    @Query("SELECT * FROM LabelDbEntity")
    fun getAllLabels(): List<LabelDbEntity>
}