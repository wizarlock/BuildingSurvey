package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectIcon() {
    IconButton(
        onClick = {  }
    ) {
        Icon(
            imageVector = Icons.Filled.List,
            contentDescription = "Select",
            modifier = Modifier
                .size(32.dp)
        )
    }
}