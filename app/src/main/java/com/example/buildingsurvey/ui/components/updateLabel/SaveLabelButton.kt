package com.example.buildingsurvey.ui.components.updateLabel

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.components.oftenUsed.DefaultButton

@Composable
fun SaveLabelButton(
    onSaveClick: () -> Unit
) {
    DefaultButton(
        text = stringResource(id = R.string.save),
        minWidth = 240.dp,
        onClick = onSaveClick
    )
}
