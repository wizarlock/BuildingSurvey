package com.example.buildingsurvey.data.model

import java.util.UUID

data class Label (
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var drawingFilePath: String = "",
    var drawingId: String = "",
    var x: Float = 0f,
    var y: Float = 0f
)
