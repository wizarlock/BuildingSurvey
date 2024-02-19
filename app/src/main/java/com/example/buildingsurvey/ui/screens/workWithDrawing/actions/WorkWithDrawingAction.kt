package com.example.buildingsurvey.ui.screens.workWithDrawing.actions

import androidx.compose.ui.geometry.Offset
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.TypeOfDefect

sealed class WorkWithDrawingAction {

    data class UpdateDrawing(val drawing: Drawing) : WorkWithDrawingAction()

    data class UpdateLabel(val label: Label) : WorkWithDrawingAction()

    data class StartRecord(val name: String) : WorkWithDrawingAction()

    data class UpdateScaleAndOffset (val pan: Offset, val zoom: Float) : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()

    object UpdatePhotoMode: WorkWithDrawingAction()

    object UpdateSwipeMode: WorkWithDrawingAction()

    object ReturnBackScaleAndOffset: WorkWithDrawingAction()

    data class UpdateAudioNum(val num: Int) : WorkWithDrawingAction()

    data class CreateLabel(val path: String, val x: Float, val y: Float, val width: Float, val height: Float,) : WorkWithDrawingAction()

    data class UpdateSelectedTypeOfDefect(val typeOfDefect: TypeOfDefect) : WorkWithDrawingAction()
}