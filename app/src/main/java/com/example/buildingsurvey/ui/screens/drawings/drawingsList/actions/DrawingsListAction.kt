package com.example.buildingsurvey.ui.screens.drawings.drawingsList.actions

import com.example.buildingsurvey.data.model.Drawing

sealed class DrawingsListAction {
    data class DeleteDrawing(val drawing: Drawing) : DrawingsListAction()
    data class StartRecord(val name: String) : DrawingsListAction()
    data class UpdateAudioNum(val num: Int) : DrawingsListAction()

    object StopRecord : DrawingsListAction()
    object DeleteProject : DrawingsListAction()
}