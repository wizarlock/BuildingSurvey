package com.example.buildingsurvey.ui.components.workWithDrawing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun DrawingImage(
    uiState: WorkWithDrawingUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
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