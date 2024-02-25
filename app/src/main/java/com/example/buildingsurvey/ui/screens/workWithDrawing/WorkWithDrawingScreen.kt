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
                startRecord = {
                    Toast.makeText(context, start, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(WorkWithDrawingAction.StartRecord(uiState.audioNum.toString()))
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateAudioNum(uiState.audioNum + 1))
                },
                stopRecord = {
                    Toast.makeText(context, stop, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(WorkWithDrawingAction.StopRecord)
                },
                updatePhotoMode = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdatePhotoMode)
                },
                updateSwipeMode = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateSwipeMode)
                },
                returnBackScale = {
                    viewModel.onUiAction(WorkWithDrawingAction.ReturnBackScaleAndOffset)
                },
                back = { value ->
                    viewModel.onUiAction(WorkWithDrawingAction.Back(value))
                },
                forward = { value ->
                    viewModel.onUiAction(WorkWithDrawingAction.Forward(value))
                },
                export = {
                    viewModel.onUiAction(WorkWithDrawingAction.Export)
                }
            )
        },

        bottomBar = {
            BotAppBarWorkWithDrawing(
                uiState = uiState,
                updateSelectedTypeOfDefect = { typeOfDefect ->
                    viewModel.onUiAction(
                        WorkWithDrawingAction.UpdateSelectedTypeOfDefect(
                            typeOfDefect
                        )
                    )
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
                acceptForBrokenLine = {

                    val allPoints =
                        uiState.linesForBrokenLine.flatMap { listOf(it.first, it.second) }
                    val result = mutableListOf(allPoints[0])
                    for (i in 1 until allPoints.size) {
                        if (allPoints[i] != allPoints[i - 1]) {
                            result.add(allPoints[i])
                        }
                    }
                    viewModel.onUiAction(
                        WorkWithDrawingAction.AddDefect(
                            isClosed = false,
                            points = result
                        )
                    )
                    viewModel.onUiAction(
                        WorkWithDrawingAction.UpdateLinesForBrokenLine(
                            linesForBrokenLine = listOf()
                        )
                    )
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateDrawingBrokenLine(isDrawing = false))
                },
                declineForBrokenLine = {
                    viewModel.onUiAction(
                        WorkWithDrawingAction.UpdateLinesForBrokenLine(
                            linesForBrokenLine = listOf()
                        )
                    )
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateDrawingBrokenLine(isDrawing = false))
                },
                connect = {
                    val allPoints =
                        uiState.linesForBrokenLine.flatMap { listOf(it.first, it.second) }
                    val result = mutableListOf(allPoints[0])
                    for (i in 1 until allPoints.size) {
                        if (allPoints[i] != allPoints[i - 1]) {
                            result.add(allPoints[i])
                        }
                    }
                    viewModel.onUiAction(
                        WorkWithDrawingAction.AddDefect(
                            isClosed = true,
                            points = result
                        )
                    )
                    viewModel.onUiAction(
                        WorkWithDrawingAction.UpdateLinesForBrokenLine(
                            linesForBrokenLine = listOf()
                        )
                    )
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateDrawingBrokenLine(isDrawing = false))
                },
                acceptForText = {
                    if (viewModel.areFieldsValid()) {
                        viewModel.onUiAction( WorkWithDrawingAction.AddText)
                    }
                },
                declineForText = {
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateText(""))
                    viewModel.onUiAction(
                        WorkWithDrawingAction.UpdateCoordinatesOfText(
                            Pair(-1f, -1f)
                        )
                    )
                },

                textChange = { text ->
                    viewModel.onUiAction(WorkWithDrawingAction.UpdateText(text))
                }
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