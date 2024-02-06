package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import android.Manifest
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordAudioIcon(
    startRecord: () -> Unit,
    stopRecord: () -> Unit
) {
    var counter by remember { mutableStateOf(0) }
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    IconButton(
        onClick = {
            if (permissionState.hasPermission) {
                counter++
                if (counter % 2 != 0) {
                    startRecord()
                } else {
                    stopRecord()
                }
            } else {
                permissionState.launchPermissionRequest()
            }
        }
    ) {
        Image(
            painter = if (counter % 2 == 0) painterResource(id = R.drawable.audio_off)
            else painterResource(id = R.drawable.audio_on),
            contentDescription = "RecordAudio",
            modifier = Modifier
                .size(32.dp)
        )
    }
}