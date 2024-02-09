package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun DrawingImage(
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit,
) {
    Box(
        modifier = boxForAllModifier(
            photoMode = uiState.photoMode,
            uiAction = uiAction
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = drawingImageModifier(
                photoMode = uiState.photoMode,
                uiState = uiState
            )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        uiState.currentDrawing.drawingFilePath
                    )
                    .size(Size.ORIGINAL)
                    .build(),
                modifier = Modifier
                    .align(Alignment.Center),
                contentDescription = null,
            )
        }
    }
}