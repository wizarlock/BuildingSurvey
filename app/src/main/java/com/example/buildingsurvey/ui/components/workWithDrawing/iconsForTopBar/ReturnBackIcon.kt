package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R

@Composable
fun ReturnBackIcon(
    returnBackScale: () -> Unit
) {
    IconButton(
        onClick = { returnBackScale() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.return_back),
            contentDescription = "ReturnBack",
            modifier = Modifier
                .size(32.dp)
        )
    }
}