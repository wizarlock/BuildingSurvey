package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AudioDbEntity(
    @PrimaryKey val id: String,
    var name: String,
    var audioFilePath: String,
    var projectId: String,
    var drawingId: String
)