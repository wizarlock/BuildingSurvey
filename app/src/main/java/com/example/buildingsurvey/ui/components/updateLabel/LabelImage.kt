package com.example.buildingsurvey.ui.components.updateLabel

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun LabelImage(
    filePath: String
) {
    val painter = rememberAsyncImagePainter(model = filePath)
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .border(2.dp, Color.Black)
            .background(Color.White)
            .paint(
                painter = painter,
                contentScale = ContentScale.FillBounds
            )
    )
}