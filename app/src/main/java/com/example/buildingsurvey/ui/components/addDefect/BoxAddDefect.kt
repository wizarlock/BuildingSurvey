package com.example.buildingsurvey.ui.components.addDefect

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
import com.example.buildingsurvey.ui.screens.addDefect.AddDefectUiState
import com.example.buildingsurvey.ui.screens.addDefect.actions.AddDefectAction

@Composable
fun BoxAddDefect(
    uiState: AddDefectUiState,
    uiAction: (AddDefectAction) -> Unit,
    onSavingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldForFilling(
            label = stringResource(id = R.string.defect_name),
            text = uiState.defectName,
            isValid = (uiState.isValidDefectName && uiState.isNotRepeatDefectName),
            onValueChange = { text ->
                uiAction(AddDefectAction.UpdateDefectName(text))
            },
            typeOfKeyboard = KeyboardType.Text,
            length = 30
        )

        if (!(uiState.isValidDefectName && uiState.isNotRepeatDefectName))
            Text(
                text = if (!uiState.isValidDefectName)
                    stringResource(id = R.string.incorrect_defect_name)
                else stringResource(id = R.string.repeat_defect_name),
                fontSize = 10.sp,
                color = Color.Red
            )

        ColorPicker(
            color = uiState.defectColorHexCode,
            isValid = (uiState.isValidDefectColorHexCode && uiState.isNotRepeatDefectColorHexCode),
            uiAction = uiAction
        )

        if (!(uiState.isValidDefectColorHexCode && uiState.isNotRepeatDefectColorHexCode))
            Text(
                text = if (!uiState.isValidDefectColorHexCode) stringResource(id = R.string.layer_color_not_selected)
                else stringResource(id = R.string.repeat_defect_color),
                fontSize = 10.sp,
                color = Color.Red
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