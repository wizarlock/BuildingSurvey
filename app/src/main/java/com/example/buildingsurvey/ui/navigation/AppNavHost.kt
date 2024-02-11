package com.example.buildingsurvey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.buildingsurvey.data.navigation.AddDrawing
import com.example.buildingsurvey.data.navigation.AddProject
import com.example.buildingsurvey.data.navigation.DrawingsList
import com.example.buildingsurvey.data.navigation.ProjectsList
import com.example.buildingsurvey.data.navigation.Start
import com.example.buildingsurvey.data.navigation.UpdateLabel
import com.example.buildingsurvey.data.navigation.WorkWithDrawing
import com.example.buildingsurvey.ui.screens.drawings.addDrawing.AddDrawingScreen
import com.example.buildingsurvey.ui.screens.drawings.drawingsList.DrawingsListScreen
import com.example.buildingsurvey.ui.screens.projects.addProject.AddProjectScreen
import com.example.buildingsurvey.ui.screens.projects.projectsList.ProjectsListScreen
import com.example.buildingsurvey.ui.screens.start.StartScreen
import com.example.buildingsurvey.ui.screens.updateLabel.UpdateLabelScreen
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingScreen

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Start.route
    ) {
        composable(Start.route) {
            StartScreen(navController = navController)
        }
        composable(ProjectsList.route) {
            ProjectsListScreen(navController = navController)
        }
        composable(AddProject.route) {
            AddProjectScreen(navController = navController)
        }
        composable(DrawingsList.route) {
            DrawingsListScreen(navController = navController)
        }
        composable(AddDrawing.route) {
            AddDrawingScreen(navController = navController)
        }
        composable(WorkWithDrawing.route) {
            WorkWithDrawingScreen(navController = navController)
        }
        composable(UpdateLabel.route) {
            UpdateLabelScreen(navController = navController)
        }
    }
}