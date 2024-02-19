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
fun SwipeIcon(
    updateSwipeMode: () -> Unit,
    uiState: WorkWithDrawingUiState
) {
    IconButton(
        onClick = {
            updateSwipeMode()
        }
    ) {
        Image(
            painter = if (!uiState.swipeMode) painterResource(id = R.drawable.swipe_off)
            else painterResource(id = R.drawable.swipe_on),
            contentDescription = "Swipe",
            modifier = Modifier
                .size(32.dp)
        )
    }
}