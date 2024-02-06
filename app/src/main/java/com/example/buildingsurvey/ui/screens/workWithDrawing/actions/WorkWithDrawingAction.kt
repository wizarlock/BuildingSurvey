package com.example.buildingsurvey.ui.screens.workWithDrawing.actions

sealed class WorkWithDrawingAction {

    data class StartRecord(val name: String) : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()

    data class UpdateAudioNum(val num: Int) : WorkWithDrawingAction()
}