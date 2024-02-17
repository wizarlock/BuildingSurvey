package com.example.buildingsurvey.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.TypeOfDefectDbEntity

@Dao
interface TypeOfDefectDao {
    @Insert
    fun insert(typeOfDefect: TypeOfDefectDbEntity)

    @Delete
    fun delete(typeOfDefect: TypeOfDefectDbEntity)

    @Query("SELECT * FROM TypeOfDefectDbEntity")
    fun getAllTypes(): List<TypeOfDefectDbEntity>
}