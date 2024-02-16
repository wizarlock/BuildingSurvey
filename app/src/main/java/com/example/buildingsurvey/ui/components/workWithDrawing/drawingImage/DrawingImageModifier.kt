package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.FileProvider
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import java.io.File
import java.util.UUID

@Composable
fun drawingImageModifier(
    photoMode: Boolean,
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit
): Modifier {
    val context = LocalContext.current
    val currentDensity = LocalDensity.current
    val currentPhotoPath = remember { mutableStateOf("") }
    val x = remember { mutableStateOf(0f) }
    val y = remember { mutableStateOf(0f) }
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            uiAction(
                WorkWithDrawingAction.CreateLabel(
                    path = currentPhotoPath.value,
                    x = x.value,
                    y = y.value,
                    width = width.value,
                    height = height.value
                )
            )
        }
    }

    val defaultImageModifier = Modifier
        .graphicsLayer(
            scaleX = maxOf(1f, minOf(20f, uiState.scale)),
            scaleY = maxOf(1f, minOf(20f, uiState.scale)),
            translationX = uiState.offsetX,
            translationY = uiState.offsetY
        )

    return if (photoMode) {
        defaultImageModifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                val offsetInDp = with(currentDensity) {
                    Offset(offset.x / density, offset.y / density)
                }
                x.value = offsetInDp.x
                y.value = offsetInDp.y
                width.value = size.width.toDp().value
                height.value = size.height.toDp().value
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
            }
        }
    } else defaultImageModifier
}