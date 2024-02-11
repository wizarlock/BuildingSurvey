package com.example.buildingsurvey.ui.screens.updateLabel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.navigation.WorkWithDrawing
import com.example.buildingsurvey.ui.components.oftenUsed.AlertDialogForDelete
import com.example.buildingsurvey.ui.components.updateLabel.LabelImage
import com.example.buildingsurvey.ui.components.updateLabel.LabelName
import com.example.buildingsurvey.ui.components.updateLabel.RowOfActions
import com.example.buildingsurvey.ui.components.updateLabel.SaveLabelButton
import com.example.buildingsurvey.ui.screens.updateLabel.actions.UpdateLabelAction

@Composable
fun UpdateLabelScreen(navController: NavHostController) {
    val viewModel: UpdateLabelViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LabelName(
            name = uiState.label.name
        )
        LabelImage(
            filePath = uiState.newPhotoPath
        )
        RowOfActions(
            onDeleteClick = {
                openDialog.value = true
            },
            uiAction = viewModel::onUiAction
        )
        SaveLabelButton(
            onSaveClick = {
                if (uiState.newPhotoPath != uiState.label.labelFilePath) viewModel.onUiAction(
                    UpdateLabelAction.SaveLabel
                )
                navController.navigate(WorkWithDrawing.route) { popUpTo(WorkWithDrawing.route) }
            }
        )
    }
    if (openDialog.value)
        AlertDialogForDelete(
            onDismissRequest = {
                openDialog.value = false
            },
            onConfirmation = {
                viewModel.onUiAction(UpdateLabelAction.DeleteLabel)
                openDialog.value = false
                navController.navigate(WorkWithDrawing.route) { popUpTo(WorkWithDrawing.route) }
            },

            title = stringResource(id = R.string.delete_label_title),
            text = stringResource(id = R.string.delete_label_text, uiState.label.name)
        )
}