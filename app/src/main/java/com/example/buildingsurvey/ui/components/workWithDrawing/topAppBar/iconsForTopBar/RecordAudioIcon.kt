package com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordAudioIcon(
    uiState: WorkWithDrawingUiState,
    startRecord: () -> Unit,
    stopRecord: () -> Unit
) {
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                if (!uiState.audioMode) Color.Transparent
                else Color.Red,
                CircleShape
            )
            .border(2.dp, Color.Black, CircleShape)
            .clickable(
                onClick = {
                    if (!uiState.drawingBrokenLine) {
                        if (permissionState.hasPermission)
                            if (!uiState.audioMode) startRecord()
                            else stopRecord()
                        else permissionState.launchPermissionRequest()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (!uiState.audioMode) painterResource(id = R.drawable.audio_start)
            else painterResource(id = R.drawable.audio_stop),
            contentDescription = "audio",
            modifier = Modifier.size(32.dp).padding(4.dp)
        )
    }
}