package com.example.buildingsurvey.ui.screens.drawings.drawingsList

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.navigation.AddDrawing
import com.example.buildingsurvey.data.navigation.WorkWithDrawing
import com.example.buildingsurvey.ui.components.drawings.drawingsList.ActionButtonsForDrawingsList
import com.example.buildingsurvey.ui.components.drawings.drawingsList.BotDrawingBar
import com.example.buildingsurvey.ui.components.drawings.drawingsList.SelectDrawing
import com.example.buildingsurvey.ui.components.myTopAppBar.MyTopAppBar
import com.example.buildingsurvey.ui.components.projects.projectsList.AlertDialogForDelete
import com.example.buildingsurvey.ui.screens.drawings.drawingsList.actions.DrawingsListAction

@Composable
fun DrawingsListScreen(
    navController: NavHostController
) {
    val viewModel: DrawingsListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val drawingForDelete = remember { mutableStateOf<Drawing?>(null) }
    val context = LocalContext.current
    val start = stringResource(id = R.string.audio_start)
    val stop = stringResource(id = R.string.audio_stop)

    Scaffold(
        topBar = {
            MyTopAppBar(
                onSettingsClick = { /*TODO*/ },
                onAvatarClick = { /*TODO*/ }
            )
        },

        bottomBar = {
            BotDrawingBar()
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = uiState.projectName,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                SelectDrawing(
                    list = uiState.drawings.collectAsState().value,
                    onDrawingClick = { drawing ->
                        viewModel.onUiAction(DrawingsListAction.UpdateDrawing(drawing = drawing))
                        navController.navigate(WorkWithDrawing.route)
                    },
                    onCloseClick = { drawing ->
                        drawingForDelete.value = drawing
                        openDialog.value = true
                    }
                )
            }

            ActionButtonsForDrawingsList(
                onAddDrawingClick = { navController.navigate(AddDrawing.route) },
                onProjectSettingsClick = { },
                startRecord = {
                    Toast.makeText(context, start, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(DrawingsListAction.StartRecord(uiState.audioNum.toString()))
                    viewModel.onUiAction(DrawingsListAction.UpdateAudioNum(uiState.audioNum + 1))
                },
                stopRecord = {
                    Toast.makeText(context, stop, Toast.LENGTH_SHORT).show()
                    viewModel.onUiAction(DrawingsListAction.StopRecord)
                }
            )
        }
    }

    if (openDialog.value)
        AlertDialogForDelete(
            onDismissRequest = {
                openDialog.value = false
            },
            onConfirmation = {
                viewModel.onUiAction(DrawingsListAction.DeleteDrawing(drawing = drawingForDelete.value!!))
                openDialog.value = false
            },

            title = stringResource(id = R.string.delete_drawing_title),
            text = stringResource(id = R.string.delete_drawing_text, drawingForDelete.value!!.name)
        )
}

