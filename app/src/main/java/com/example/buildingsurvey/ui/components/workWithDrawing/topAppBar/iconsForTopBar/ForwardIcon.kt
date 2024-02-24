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
import com.example.buildingsurvey.data.model.Defect
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun ForwardIcon(
    uiState: WorkWithDrawingUiState,
    returnDefect: (Defect) -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(
                enabled = uiState.isForwardEnable,
                onClick = {
                    if (!uiState.drawingBrokenLine) {
                        val defect = uiState.changesList.forward()
                        returnDefect(defect as Defect)
                    }
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