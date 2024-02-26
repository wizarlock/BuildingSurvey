package com.example.buildingsurvey.data.model

import java.util.UUID

data class Defect (
    val id: String = UUID.randomUUID().toString(),
    val drawingId: String = "",
    val isClosed: Int = 0,
    val hexCode: String = ""
)
