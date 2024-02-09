package com.example.buildingsurvey.ui.screens.workWithDrawing.actions

import androidx.compose.ui.geometry.Offset
import com.example.buildingsurvey.data.model.Drawing

sealed class WorkWithDrawingAction {

    data class UpdateDrawing(val drawing: Drawing) : WorkWithDrawingAction()

    data class StartRecord(val name: String) : WorkWithDrawingAction()

    data class UpdateScaleAndOffset (val pan: Offset, val zoom: Float) : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()

    object UpdatePhotoMode: WorkWithDrawingAction()

    object ReturnBackScaleAndOffset: WorkWithDrawingAction()

    data class UpdateAudioNum(val num: Int) : WorkWithDrawingAction()
}