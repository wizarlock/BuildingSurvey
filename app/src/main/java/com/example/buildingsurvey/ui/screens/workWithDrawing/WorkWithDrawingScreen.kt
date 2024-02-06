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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.components.workWithDrawing.BotAppBarWorkWithDrawing
import com.example.buildingsurvey.ui.components.workWithDrawing.DrawingImage
import com.example.buildingsurvey.ui.components.workWithDrawing.TopAppBarForWorkWithDrawing
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun WorkWithDrawingScreen (
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
               startRecord = {
                   Toast.makeText(context, start, Toast.LENGTH_SHORT).show()
                   viewModel.onUiAction(WorkWithDrawingAction.StartRecord(uiState.audioNum.toString()))
                   viewModel.onUiAction(WorkWithDrawingAction.UpdateAudioNum(uiState.audioNum + 1))
               },
               stopRecord = {
                   Toast.makeText(context, stop, Toast.LENGTH_SHORT).show()
                   viewModel.onUiAction(WorkWithDrawingAction.StopRecord)
               }
           )
        },

        bottomBar = {
            BotAppBarWorkWithDrawing()
        }
    )
    { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            DrawingImage(
                uiState = uiState
            )
        }
    }
}