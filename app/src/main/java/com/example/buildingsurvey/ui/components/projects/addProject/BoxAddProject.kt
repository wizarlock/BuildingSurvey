package com.example.buildingsurvey.ui.components.projects.addProject

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.components.oftenUsed.DefaultButton
import com.example.buildingsurvey.ui.components.oftenUsed.TextFieldForFilling
import com.example.buildingsurvey.ui.screens.projects.addProject.AddProjectViewModel
import com.example.buildingsurvey.ui.screens.projects.addProject.actions.AddProjectAction

@Composable
fun BoxAddProject(
    uiState: AddProjectViewModel.AddProjectUiState,
    uiAction: (AddProjectAction) -> Unit,
    onSavingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldForFilling(
            label = stringResource(id = R.string.project_name),
            text = uiState.projectName,
            isValid = (uiState.isValidProjectName && uiState.isNotRepeatProjectName),
            onValueChange = { text ->
                uiAction(AddProjectAction.UpdateProjectName(text))
            },
            typeOfKeyboard = KeyboardType.Text,
            length = 60
        )

        if (!(uiState.isValidProjectName && uiState.isNotRepeatProjectName))
            Text(
                text = if (!uiState.isValidProjectName)
                    stringResource(id = R.string.incorrect_project_name)
                else stringResource(id = R.string.repeat_project_name),
                fontSize = 10.sp,
                color = Color.Red
            )

        LoadDropdownMenu(
            loadFile = { uri ->
                uiAction(AddProjectAction.UpdateSelectedFile(uri))
            },
            takePhoto = { path ->
                uiAction(AddProjectAction.UpdatePhotoFile(path))
            },
            textIfFilled = stringResource(id = R.string.loaded_project_photo),
            textIfNotFilled = stringResource(id = R.string.not_loaded_project_photo),
            isFilled = uiState.isFileExists
        )

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            DefaultButton(
                text = stringResource(id = R.string.save),
                minWidth = 240.dp,
                onClick = onSavingClick
            )
        }
    }
}