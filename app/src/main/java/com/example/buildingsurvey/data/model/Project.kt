package com.example.buildingsurvey.data.model

import java.util.UUID

data class Project(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var projectFilePath: String = ""
)
