package com.example.buildingsurvey.ui.components.drawings.drawingsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.components.oftenUsed.DefaultButton

@Composable
fun ActionButtonsForDrawingsList(
    onAddDrawingClick: () -> Unit,
    onProjectSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DefaultButton(
            text = stringResource(id = R.string.add_drawing),
            onClick = { onAddDrawingClick() }
        )
        DefaultButton(
            text = stringResource(id = R.string.project_settings),
            onClick = { onProjectSettingsClick() }
        )
        AudioContextButton()
    }
}