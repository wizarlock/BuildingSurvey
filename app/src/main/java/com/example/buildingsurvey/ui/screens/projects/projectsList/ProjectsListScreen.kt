package com.example.buildingsurvey.ui.screens.projects.projectsList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.data.navigation.AddProject
import com.example.buildingsurvey.data.navigation.DrawingsList
import com.example.buildingsurvey.ui.components.myTopAppBar.MyTopAppBar
import com.example.buildingsurvey.ui.components.projects.projectsList.AlertDialogForDelete
import com.example.buildingsurvey.ui.components.projects.projectsList.BotAppBarForProjects
import com.example.buildingsurvey.ui.components.projects.projectsList.ListOfProjects
import com.example.buildingsurvey.ui.screens.projects.projectsList.actions.ProjectsAction

@Composable
fun ProjectsListScreen(
    navController: NavHostController
) {
    val viewModel: ProjectsListViewModel = hiltViewModel()
    val openDialog = remember { mutableStateOf(false) }
    val projectForDelete = remember { mutableStateOf<Project?>(null) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                onSettingsClick = { },
                onAvatarClick = { }
            )
        },

        bottomBar = {
            BotAppBarForProjects(onAddClick = { navController.navigate(AddProject.route) })
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
                text = stringResource(id = R.string.my_projects),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            ListOfProjects(
                projects = viewModel.projects.collectAsState().value,
                onCardClick = { project ->
                    viewModel.onUiAction(ProjectsAction.UpdateProject(project = project))
                    navController.navigate(DrawingsList.route)
                },
                onDeleteClick = { project ->
                    projectForDelete.value = project
                    openDialog.value = true
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
                viewModel.onUiAction(ProjectsAction.DeleteProject(project = projectForDelete.value!!))
                openDialog.value = false
            },
            title = stringResource(id = R.string.delete_project_title),
            text = stringResource(id = R.string.delete_project_text, projectForDelete.value!!.name)
        )
}