package com.example.buildingsurvey.ui.components.drawings.drawingsList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
    onProjectSettingsClick: () -> Unit,
    startRecord: () -> Unit,
    stopRecord: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 100.dp, end = 10.dp, start = 10.dp, bottom = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultButton(
            text = stringResource(id = R.string.add_drawing),
            minWidth = 300.dp,
            onClick = { onAddDrawingClick() }
        )

        Spacer(modifier = Modifier.padding(top = 40.dp))

        DefaultButton(
            text = stringResource(id = R.string.project_settings),
            minWidth = 300.dp,
            onClick = { onProjectSettingsClick() }
        )

        Spacer(modifier = Modifier.padding(top = 40.dp))

        AudioContextButton(
            startRecord = startRecord,
            stopRecord = stopRecord
        )

        Spacer(modifier = Modifier.padding(top = 40.dp))
    }
}