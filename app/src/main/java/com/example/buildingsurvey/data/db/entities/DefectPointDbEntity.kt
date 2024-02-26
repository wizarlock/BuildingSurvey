package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DefectPointDbEntity(
    @PrimaryKey val id: String,
    var defectId: String,
    var drawingId: String,
    var position: Int,
    var xInApp: Float,
    var yInApp: Float,
    var xReal: Float,
    var yReal: Float
)




