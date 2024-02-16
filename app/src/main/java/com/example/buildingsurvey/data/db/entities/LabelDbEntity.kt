package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LabelDbEntity(
    @PrimaryKey val id: String,
    var name: String,
    var labelFilePath: String,
    var drawingId: String,
    var xInApp: Float,
    var yInApp: Float,
    var xReal: Float,
    var yReal: Float
)