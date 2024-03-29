package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun labelModifier(
    uiState: WorkWithDrawingUiState,
    label: Label,
    onLabelClick: (Label) -> Unit
): Modifier {
    val defaultLabelModifier = Modifier
        .offset(
            x = label.xInApp.dp - 2.dp,
            y = label.yInApp.dp - 2.dp
        )
        .size(4.dp)
        .background(
            color = Color.Red,
            shape = CircleShape
        )
        .border(
            width = 0.2.dp,
            color = Color.Black,
            shape = CircleShape
        )

    return if (
        !uiState.photoMode &&
        !uiState.swipeMode &&
        !uiState.lineSegmentSelected &&
        !uiState.brokenLineSelected &&
        !uiState.pointDefectSelected &&
        !uiState.frameSelected &&
        !uiState.textSelected
    )
        defaultLabelModifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    onLabelClick(label)
                }
            )
        }
    else defaultLabelModifier
}
