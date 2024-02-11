package com.example.buildingsurvey.ui.screens.updateLabel.actions

sealed class UpdateLabelAction {
    data class TakePhoto (val photoPath: String) : UpdateLabelAction()

    object SaveLabel : UpdateLabelAction()
    object DeleteLabel : UpdateLabelAction()
}