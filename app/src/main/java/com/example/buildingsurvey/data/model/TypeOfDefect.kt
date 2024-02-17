package com.example.buildingsurvey.data.model

import java.util.UUID

data class TypeOfDefect(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val hexCode: String = "",
)