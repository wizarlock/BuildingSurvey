package com.example.buildingsurvey.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.buildingsurvey.data.db.entities.ProjectDbEntity

@Dao
interface ProjectDao {
    @Insert
    fun insert(project: ProjectDbEntity)

    @Delete
    fun delete(project: ProjectDbEntity)

    @Query("SELECT * FROM ProjectDbEntity")
    fun getAllProjects(): List<ProjectDbEntity>
}