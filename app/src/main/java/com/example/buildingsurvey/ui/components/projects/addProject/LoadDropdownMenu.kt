package com.example.buildingsurvey.ui.components.projects.addProject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.buildingsurvey.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun LoadDropdownMenu(
    textIfFilled: String,
    textIfNotFilled: String,
    isFilled: Boolean,
    loadFile: (Uri?) -> Unit,
    takePhoto: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            loadFile(uri)
        }
    val context = LocalContext.current
    val currentPhotoPath = remember { mutableStateOf("") }
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            takePhoto(currentPhotoPath.value)
        }
    }

    Box(
        modifier = Modifier.padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = if (isFilled) textIfFilled else textIfNotFilled,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    if (isFilled) {
                        Icon(Icons.Rounded.CheckCircle, null)
                    } else {
                        Icon(Icons.Rounded.Add, null)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.take_photo),
                            fontSize = 18.sp
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.take_photo),
                            null
                        )
                    },

                    onClick = {
                        if (permissionState.hasPermission) {
                            expanded = false
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
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.load_from_files),
                            fontSize = 18.sp
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.load_file),
                            null
                        )
                    },
                    onClick = {
                        expanded = false
                        launcher.launch("image/*")
                    }
                )
            }
        }
    }
}

