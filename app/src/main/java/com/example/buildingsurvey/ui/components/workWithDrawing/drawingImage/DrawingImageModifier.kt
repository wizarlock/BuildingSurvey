package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun drawingImageModifier(
    photoMode: Boolean,
    uiState: WorkWithDrawingUiState
): Modifier {
    val alwaysImageModifier = Modifier
        .graphicsLayer(
            scaleX = maxOf(1f, minOf(20f, uiState.scale)),
            scaleY = maxOf(1f, minOf(20f, uiState.scale)),
            translationX = uiState.offsetX,
            translationY = uiState.offsetY
        )

    return alwaysImageModifier
}