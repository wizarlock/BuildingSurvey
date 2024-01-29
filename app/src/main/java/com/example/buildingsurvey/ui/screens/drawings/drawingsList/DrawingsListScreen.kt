package com.example.buildingsurvey.ui.screens.drawings.drawingsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.buildingsurvey.data.navigation.AddDrawing
import com.example.buildingsurvey.ui.components.drawings.drawingsList.ActionButtonsForDrawingsList
import com.example.buildingsurvey.ui.components.drawings.drawingsList.BotDrawingBar
import com.example.buildingsurvey.ui.components.drawings.drawingsList.SelectDrawing
import com.example.buildingsurvey.ui.components.myTopAppBar.MyTopAppBar

@Composable
fun DrawingsListScreen(
    navController: NavHostController
) {
    val viewModel: DrawingsListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

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
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "uiState.projectName",
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            SelectDrawing(
                list = listOf(),
                onDrawingClick = { },
                onCloseClick = { }
            )
            ActionButtonsForDrawingsList(
                onAddDrawingClick = { navController.navigate(AddDrawing.route) },
                onProjectSettingsClick = { }
            )
        }
    }
}

