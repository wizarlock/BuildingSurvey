package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectDbEntity(
    @PrimaryKey val id: String,
    var name: String,
    var projectFilePath: String
)
