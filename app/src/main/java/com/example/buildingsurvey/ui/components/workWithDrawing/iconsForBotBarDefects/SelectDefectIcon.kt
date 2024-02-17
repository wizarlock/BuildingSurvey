package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SelectDefectIcon(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.White)
            .border(2.dp, Color.Black)
            .clickable(onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            drawCircle(
                color = Color.Red,
                radius = size.minDimension / 2f
            )
            drawCircle(
                color = Color.Black,
                radius = size.minDimension / 2f,
                style = Stroke(2.dp.toPx())
            )
        }
    }
}