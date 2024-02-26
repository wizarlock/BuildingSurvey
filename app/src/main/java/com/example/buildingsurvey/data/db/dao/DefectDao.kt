package com.example.buildingsurvey.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.DefectDbEntity

@Dao
interface DefectDao {
    @Insert
    fun insert(defect: DefectDbEntity)

    @Delete
    fun delete(defect: DefectDbEntity)

    @Query("SELECT * FROM DefectDbEntity")
    fun getAllDefects(): List<DefectDbEntity>
}