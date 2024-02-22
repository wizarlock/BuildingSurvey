package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

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

@Composable
fun ForwardIcon() {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(
                onClick = {

                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.forward),
            contentDescription = "Forward",
            modifier = Modifier.size(32.dp)
        )
    }
}