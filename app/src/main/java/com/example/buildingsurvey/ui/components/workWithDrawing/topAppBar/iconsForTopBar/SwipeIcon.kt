package com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
fun SwipeIcon(
    updateSwipeMode: () -> Unit,
    uiState: WorkWithDrawingUiState
) {
    Box(
        modifier = Modifier
            .background(
                if (!uiState.swipeMode) Color.Transparent
                else Color.Green
            )
            .border(2.dp, Color.Black)
            .clickable(
                onClick = {
                    updateSwipeMode()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.swipe),
            contentDescription = "swipe",
            modifier = Modifier.size(32.dp).padding(4.dp)
        )
    }
}