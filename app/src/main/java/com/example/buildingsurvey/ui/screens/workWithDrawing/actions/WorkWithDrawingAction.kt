package com.example.buildingsurvey.ui.screens.workWithDrawing.actions

import com.example.buildingsurvey.data.model.Drawing

sealed class WorkWithDrawingAction {

    data class UpdateDrawing(val drawing: Drawing) : WorkWithDrawingAction()

    data class StartRecord(val name: String) : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()

    data class UpdateAudioNum(val num: Int) : WorkWithDrawingAction()
}