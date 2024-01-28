package com.example.buildingsurvey.ui.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.buildingsurvey.data.navigation.ProjectsList
import com.example.buildingsurvey.ui.components.start.ButtonsForStart
import com.example.buildingsurvey.ui.components.start.LogoWithNameForStart


@Composable
fun StartScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoWithNameForStart()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ButtonsForStart(
                onLogInClick = {
                    navController.navigate(ProjectsList.route) { popUpTo(ProjectsList.route) }
                }
            )
        }
    }
}
