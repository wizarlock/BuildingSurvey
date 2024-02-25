package com.example.buildingsurvey.data.model

import java.util.UUID

data class Text (
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    var drawingId: String = "",
    var xInApp: Float = 0f,
    var yInApp: Float = 0f,
    var xReal: Float = 0f,
    var yReal: Float = 0f
)