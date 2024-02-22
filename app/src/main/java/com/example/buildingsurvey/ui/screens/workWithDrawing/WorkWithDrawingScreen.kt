package com.example.buildingsurvey.ui.screens.workWithDrawing

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.navigation.AddDefect
import com.example.buildingsurvey.data.navigation.UpdateLabel
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.BotAppBarWorkWithDrawing
import com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage.DrawingImage
import com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.TopAppBarForWorkWithDrawing
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun WorkWithDrawingScreen(
    navController: NavController
) {
    val viewModel: WorkWithDrawingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val start = stringResource(id = R.string.audio_start)
    val stop = stringResource(id = R.string.audio_stop)

    Scaffold(
        topBar = {
            TopAppBarForWorkWithDrawing(
                uiState = uiState,
                selectDrawing = { drawing ->
                    if (uiState.audioMode) Toast.makeText(context, stop, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateDrawing(drawing))
                },
                currentDrawing = uiState.currentDrawing,
                listOfDrawings = uiState.drawings.collectAsState().value,
                startRecord = {
                    Toast.makeText(context, start, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(WorkWithDrawingAction.StartRecord(uiState.audioNum.toString()))
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateAudioNum(uiState.audioNum + 1))
                },
                stopRecord = {
                    Toast.makeText(context, stop, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(WorkWithDrawingAction.StopRecord)
                },
                updatePhotoMode =  {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdatePhotoMode)
                },
                updateSwipeMode =  {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateSwipeMode)
                },
                returnBackScale = {
                    viewModel.onUiAction(WorkWithDrawingAction.ReturnBackScaleAndOffset)
                }
            )
        },

        bottomBar = {
            BotAppBarWorkWithDrawing(
                uiState = uiState,
                updateSelectedTypeOfDefect = { typeOfDefect ->
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateSelectedTypeOfDefect(typeOfDefect))
                },
                addTypeOfDefect = {
                    navController.navigate(AddDefect.route)
                },
                updateTextSelected = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateTextSelected)
                },
                updateFrameSelected = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateFrameSelected)
                },
                updateBrokenLineSelected = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateBrokenLineSelected)
                },
                updateLineSegmentSelected = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateLineSegmentSelected)
                },
                updatePointDefectSelected = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdatePointDefectSelected)
                },
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DrawingImage(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                onLabelClick = { label ->
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateLabel(label = label))
                    navController.navigate(UpdateLabel.route)
                }
            )
        }
    }
}