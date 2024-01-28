package com.example.buildingsurvey.data.model

import java.util.UUID

data class Drawing (
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var projectId: String = ""
)
