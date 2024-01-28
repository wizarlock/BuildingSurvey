package com.example.buildingsurvey.ui.screens

import com.example.buildingsurvey.data.model.Project

fun isValidProjectOrDrawingName(name: String) = name.isNotEmpty()

fun isNotRepeatProjectOrDrawingName(name: String, list: List<Project>) = !list.any { it.name == name }