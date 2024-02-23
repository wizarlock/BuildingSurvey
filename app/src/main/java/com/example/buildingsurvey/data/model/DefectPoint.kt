package com.example.buildingsurvey.data.model

import java.util.UUID

data class DefectPoint (
    val id: String = UUID.randomUUID().toString(),
    val defectId: String = "",
    val drawingId: String = "",
    val position: Int = 0,
    var xInApp: Float = 0f,
    var yInApp: Float = 0f,
    var xReal: Float = 0f,
    var yReal: Float = 0f
)
