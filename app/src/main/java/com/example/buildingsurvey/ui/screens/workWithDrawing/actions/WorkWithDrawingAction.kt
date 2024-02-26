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

    data class AddDefect (val isClosed: Int, val points: List<Offset>) : WorkWithDrawingAction()

    data class Forward (val value: Any) : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()

    data class UpdateDrawingBrokenLine (val isDrawing: Boolean) : WorkWithDrawingAction()

    object UpdatePhotoMode: WorkWithDrawingAction()

    object UpdateSwipeMode: WorkWithDrawingAction()

    object UpdateTextSelected: WorkWithDrawingAction()

    object UpdateFrameSelected: WorkWithDrawingAction()

    object UpdateBrokenLineSelected: WorkWithDrawingAction()

    object UpdateLineSegmentSelected: WorkWithDrawingAction()

    object UpdatePointDefectSelected: WorkWithDrawingAction()

    object ReturnBackScaleAndOffset: WorkWithDrawingAction()

    object Export: WorkWithDrawingAction()

    data class UpdateLinesForBrokenLine (val linesForBrokenLine: List<Pair<Offset, Offset>>) : WorkWithDrawingAction()

    data class UpdateAudioNum(val num: Int) : WorkWithDrawingAction()

    data class Back(val value: Any) : WorkWithDrawingAction()

    data class CreateLabel(val path: String, val x: Float, val y: Float) : WorkWithDrawingAction()

    data class UpdateSelectedTypeOfDefect(val typeOfDefect: TypeOfDefect) : WorkWithDrawingAction()

    data class LoadAppWidthAndHeight(val width: Float, val height: Float) : WorkWithDrawingAction()

    data class UpdateText(val text: String) : WorkWithDrawingAction()

    data class UpdateCoordinatesOfText(val coordinates: Pair<Float, Float>) : WorkWithDrawingAction()

    object AddText: WorkWithDrawingAction()
}