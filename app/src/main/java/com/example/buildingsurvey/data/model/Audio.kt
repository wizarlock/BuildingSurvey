package com.example.buildingsurvey.data.model

import java.util.UUID

data class Audio (
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var audioFilePath: String = "",
    var projectId: String = "",
    var drawingId: String = ""
)
