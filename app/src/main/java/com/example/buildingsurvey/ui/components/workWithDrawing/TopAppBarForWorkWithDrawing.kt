package com.example.buildingsurvey.ui.components.workWithDrawing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.BackIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.ExportIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.ForwardIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.PhotoIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.RecordAudioIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.ReturnBackIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar.SelectIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarForWorkWithDrawing(
    startRecord: () -> Unit,
    stopRecord: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {},
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SelectIcon()
                BackIcon()
                ForwardIcon()
                RecordAudioIcon(
                    startRecord = { startRecord() },
                    stopRecord = { stopRecord() }
                )
                PhotoIcon()
                ReturnBackIcon()
                ExportIcon()
            }

        }
    )
}
