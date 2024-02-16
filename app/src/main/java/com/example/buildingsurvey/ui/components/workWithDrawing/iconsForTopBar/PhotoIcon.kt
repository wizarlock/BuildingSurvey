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
fun PhotoIcon(
    updatePhotoMode: () -> Unit,
    uiState: WorkWithDrawingUiState
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    IconButton(
        onClick = {
            if (permissionState.hasPermission) updatePhotoMode()
            else permissionState.launchPermissionRequest()
        }
    ) {
        Image(
            painter = if (!uiState.photoMode) painterResource(id = R.drawable.take_photo_off)
            else painterResource(id = R.drawable.take_photo_on),
            contentDescription = "Photo",
            modifier = Modifier
                .size(32.dp)
        )
    }
}