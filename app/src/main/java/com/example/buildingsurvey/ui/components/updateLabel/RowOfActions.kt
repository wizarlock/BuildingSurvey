package com.example.buildingsurvey.ui.components.updateLabel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.buildingsurvey.ui.screens.updateLabel.actions.UpdateLabelAction

@Composable
fun RowOfActions(
    onDeleteClick: () -> Unit,
    uiAction: (UpdateLabelAction) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DeleteLabelAction (
            onDeleteClick = onDeleteClick
        )
        ChangePhotoAction(
            uiAction = uiAction
        )
    }
}