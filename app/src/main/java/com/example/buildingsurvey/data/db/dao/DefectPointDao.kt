package com.example.buildingsurvey.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.DefectPointDbEntity

@Dao
interface DefectPointDao {
    @Insert
    fun insert(defectPoint: DefectPointDbEntity)

    @Delete
    fun delete(defectPoint: DefectPointDbEntity)

    @Query("SELECT * FROM DefectPointDbEntity")
    fun getAllDefectPoints(): List<DefectPointDbEntity>
}
