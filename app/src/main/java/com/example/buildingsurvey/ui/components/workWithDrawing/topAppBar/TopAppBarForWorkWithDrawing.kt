package com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.BackIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.ExportIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.ForwardIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.PhotoIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.RecordAudioIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.ReturnBackIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.SelectDrawingIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar.SwipeIcon
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarForWorkWithDrawing(
    uiState: WorkWithDrawingUiState,
    selectDrawing: (Drawing) -> Unit,
    startRecord: () -> Unit,
    stopRecord: () -> Unit,
    updatePhotoMode: () -> Unit,
    updateSwipeMode: () -> Unit,
    returnBackScale: () -> Unit,
    back: (Any) -> Unit,
    forward: (Any) -> Unit,
    export: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = {},
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectDrawingIcon(
                    selectDrawing = { drawing ->
                        selectDrawing(drawing)
                    },
                    uiState = uiState
                )

                BackIcon(
                    back = { value -> back(value) },
                    uiState = uiState
                )
                ForwardIcon(
                    uiState = uiState,
                    forward = { value -> forward(value) }
                )
                RecordAudioIcon(
                    startRecord = { startRecord() },
                    stopRecord = { stopRecord() },
                    uiState = uiState
                )
                PhotoIcon(
                    uiState = uiState,
                    updatePhotoMode = updatePhotoMode
                )
                SwipeIcon(
                    updateSwipeMode = updateSwipeMode,
                    uiState = uiState
                )
                ReturnBackIcon(
                    uiState = uiState,
                    returnBackScale = { returnBackScale() }
                )
                ExportIcon(
                    uiState = uiState,
                    export = { export() }
                )
            }
        }
    )
}
