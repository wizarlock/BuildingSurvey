package com.example.buildingsurvey.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.DrawingDbEntity

@Dao
interface DrawingDao {
    @Insert
    fun insert(drawing: DrawingDbEntity)

    @Delete
    fun delete(drawing: DrawingDbEntity)

    @Query("SELECT * FROM DrawingDbEntity")
    fun getAllDrawings(): List<DrawingDbEntity>
}