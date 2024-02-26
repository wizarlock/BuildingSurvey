package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TextDbEntity(
    @PrimaryKey val id: String,
    var text: String,
    var drawingId: String,
    var xInApp: Float,
    var yInApp: Float,
    var xReal: Float,
    var yReal: Float
)
