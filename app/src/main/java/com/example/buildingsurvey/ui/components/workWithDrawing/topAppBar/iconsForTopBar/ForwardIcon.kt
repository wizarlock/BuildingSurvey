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
fun ForwardIcon(
    uiState: WorkWithDrawingUiState,
    forward: (Any) -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(
                enabled = uiState.isForwardEnable && !uiState.drawingBrokenLine && uiState.coordinatesOfText.first == -1f && uiState.coordinatesOfText.second == -1f,
                onClick = {
                    val value = uiState.changesList.forward()
                    forward(value)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (!uiState.isForwardEnable) painterResource(id = R.drawable.forward_off)
            else painterResource(id = R.drawable.forward_on),
            contentDescription = "Forward",
            modifier = Modifier.size(32.dp)
        )
    }
}