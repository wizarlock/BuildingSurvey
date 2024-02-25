package com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun ReturnBackIcon(
    uiState: WorkWithDrawingUiState,
    returnBackScale: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(
                enabled = !uiState.drawingBrokenLine && uiState.coordinatesOfText.first == -1f && uiState.coordinatesOfText.second == -1f,
                onClick = {
                    returnBackScale()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.return_back),
            contentDescription = "ReturnBack",
            modifier = Modifier.size(32.dp)

        )
    }
}