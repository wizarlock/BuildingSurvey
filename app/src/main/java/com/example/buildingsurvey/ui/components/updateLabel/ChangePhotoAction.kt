package com.example.buildingsurvey.ui.components.updateLabel

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.screens.updateLabel.actions.UpdateLabelAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChangePhotoAction(
    uiAction: (UpdateLabelAction) -> Unit
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val currentPhotoPath = remember { mutableStateOf("") }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            uiAction(UpdateLabelAction.TakePhoto(photoPath = currentPhotoPath.value))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = stringResource(id = R.string.change_photo),
            fontSize = 20.sp,
            color = Color.Black,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            modifier = Modifier.clickable {
                if (permissionState.hasPermission) {
                    val imgFile = File.createTempFile(
                        UUID.randomUUID().toString(),
                        ".jpg",
                        context.cacheDir
                    )
                    currentPhotoPath.value = imgFile.absolutePath
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(
                            context,
                            "com.example.buildingsurvey.fileprovider",
                            imgFile
                        )
                    )
                    cameraLauncher.launch(cameraIntent)
                } else permissionState.launchPermissionRequest()
            }
        )
    }
}