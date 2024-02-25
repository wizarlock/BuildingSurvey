package com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDecisionMaking

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

@Composable
fun DeclineIcon(
    decline: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .border(2.dp, Color.Black)
            .clickable(
                onClick = {
                    decline()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.decline),
            contentDescription = "decline",
            modifier = Modifier.size(40.dp).padding(4.dp)
        )
    }
}