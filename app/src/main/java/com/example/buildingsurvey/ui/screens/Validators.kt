package com.example.buildingsurvey.ui.screens

fun isValidName(name: String) = name.isNotEmpty()

fun isNotRepeatName(name: String, list: List<String>) = !list.any { it == name }