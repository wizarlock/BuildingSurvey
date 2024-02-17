package com.example.buildingsurvey.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TypeOfDefectDbEntity (
    @PrimaryKey val id: String,
    var name: String,
    var hexCode: String,
    var projectId: String
)