package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    IconButton(
        onClick = {
            if (permissionState.hasPermission)
                if (!uiState.audioMode) startRecord()
                else stopRecord()
            else permissionState.launchPermissionRequest()

        }
    ) {
        Image(
            painter = if (!uiState.audioMode) painterResource(id = R.drawable.audio_off)
            else painterResource(id = R.drawable.audio_on),
            contentDescription = "RecordAudio",
            modifier = Modifier
                .size(32.dp)
        )
    }
}