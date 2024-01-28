package com.example.buildingsurvey.ui.screens.projects.addProject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.buildingsurvey.data.navigation.ProjectsList
import com.example.buildingsurvey.ui.components.projects.addProject.BoxAddProject
import com.example.buildingsurvey.ui.screens.projects.addProject.actions.AddProjectAction

@Composable
fun AddProjectScreen(navController: NavHostController) {
    val viewModel: AddProjectViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxAddProject(
            uiState,
            viewModel::onUiAction,
            onSavingClick = {
                if (viewModel.areFieldsValid()) {
                    viewModel.onUiAction(AddProjectAction.SaveProject)
                    navController.navigate(ProjectsList.route) { popUpTo(ProjectsList.route) }
                }
            }
        )
    }
}