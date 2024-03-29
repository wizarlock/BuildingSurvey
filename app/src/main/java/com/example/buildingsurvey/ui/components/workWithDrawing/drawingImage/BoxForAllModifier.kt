package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun boxForAllModifier(
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit
): Modifier {
    val alwaysBoxModifier = Modifier
        .fillMaxSize()
        .background(Color.White)

    return if (
        uiState.swipeMode &&
        !uiState.lineSegmentSelected &&
        !uiState.brokenLineSelected &&
        !uiState.pointDefectSelected &&
        !uiState.frameSelected &&
        !uiState.textSelected
    ) {
        alwaysBoxModifier.pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                uiAction(
                    WorkWithDrawingAction.UpdateScaleAndOffset(
                        pan = pan,
                        zoom = zoom
                    )
                )
            }
        }
    } else alwaysBoxModifier
}