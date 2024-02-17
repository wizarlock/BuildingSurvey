package com.example.buildingsurvey.ui.screens.addDefect.actions

sealed class AddDefectAction {

    data class UpdateDefectName(val name: String) : AddDefectAction()
    data class UpdateDefectColorHexCode(val hexCode: String) : AddDefectAction()

    object SaveDefect : AddDefectAction()
}