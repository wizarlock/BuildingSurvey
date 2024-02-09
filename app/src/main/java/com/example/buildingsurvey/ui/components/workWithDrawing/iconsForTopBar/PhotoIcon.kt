package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R

@Composable
fun PhotoIcon(
    updatePhotoMode: () -> Unit
) {
    var counter by remember { mutableStateOf(0) }
    IconButton(
        onClick = {
            updatePhotoMode()
            counter++
        }
    ) {
        Image(
            painter = if (counter % 2 == 0) painterResource(id = R.drawable.take_photo_off)
            else painterResource(id = R.drawable.take_photo_on),
            contentDescription = "Photo",
            modifier = Modifier
                .size(32.dp)
        )
    }
}