package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DefectDbEntity(
    @PrimaryKey val id: String,
    var drawingId: String,
    var isClosed: Int,
    var hexCode: String
)