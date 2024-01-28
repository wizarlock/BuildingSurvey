package com.example.buildingsurvey.ui.components.projects.projectsList

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.buildingsurvey.R


@Composable
fun AlertDialogForDelete(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    projectName: String
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.delete_project_title))
        },
        text = {
            Text(text = stringResource(id = R.string.delete_project_text, projectName))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(text = stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(id = R.string.no))
            }
        }
    )
}