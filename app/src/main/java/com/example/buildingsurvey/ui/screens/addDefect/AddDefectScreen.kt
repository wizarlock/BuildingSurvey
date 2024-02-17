package com.example.buildingsurvey.ui.screens.addDefect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buildingsurvey.data.navigation.WorkWithDrawing
import com.example.buildingsurvey.ui.components.addDefect.BoxAddDefect
import com.example.buildingsurvey.ui.screens.addDefect.actions.AddDefectAction

@Composable
fun AddDefectScreen(
    navController: NavController
) {
    val viewModel: AddDefectViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxAddDefect(
            uiState = uiState,
            uiAction = viewModel::onUiAction,
            onSavingClick = {
                if (viewModel.areFieldsValid()) {
                    viewModel.onUiAction(AddDefectAction.SaveDefect)
                    navController.navigate(WorkWithDrawing.route) { popUpTo(WorkWithDrawing.route) }
                }
            }
        )
    }
}