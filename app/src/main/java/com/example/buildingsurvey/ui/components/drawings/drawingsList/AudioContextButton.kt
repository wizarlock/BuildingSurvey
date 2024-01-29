package com.example.buildingsurvey.ui.components.drawings.drawingsList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R

@Composable
fun AudioContextButton() {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        onClick = { },
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .widthIn(min = 240.dp)
    ) {
        Text(
            text = stringResource(id = R.string.record_context),
            fontSize = 25.sp,
            color = Color.Black
        )
    }
}