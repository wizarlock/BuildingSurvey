package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun PhotoIcon(
    updatePhotoMode: () -> Unit,
    uiState: WorkWithDrawingUiState
) {
    IconButton(
        onClick = {
            updatePhotoMode()
        }
    ) {
        Image(
            painter = if (!uiState.photoMode) painterResource(id = R.drawable.take_photo_off)
            else painterResource(id = R.drawable.take_photo_on),
            contentDescription = "Photo",
            modifier = Modifier
                .size(32.dp)
        )
    }
}