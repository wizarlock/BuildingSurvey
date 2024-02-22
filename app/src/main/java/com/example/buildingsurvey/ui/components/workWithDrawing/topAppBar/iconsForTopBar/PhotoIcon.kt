package com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Box(
        modifier = Modifier
            .background(
                if (!uiState.photoMode) Color.Transparent
                else Color.Green
            )
            .border(2.dp, Color.Black)
            .clickable(
                onClick = {
                    if (permissionState.hasPermission) updatePhotoMode()
                    else permissionState.launchPermissionRequest()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.take_photo),
            contentDescription = "photo",
            modifier = Modifier.size(32.dp).padding(4.dp)
        )
    }
}