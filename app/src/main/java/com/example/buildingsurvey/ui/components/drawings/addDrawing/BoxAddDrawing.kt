package com.example.buildingsurvey.ui.components.drawings.addDrawing

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
import com.example.buildingsurvey.ui.screens.drawings.addDrawing.AddDrawingUiState
import com.example.buildingsurvey.ui.screens.drawings.addDrawing.actions.AddDrawingAction

@Composable
fun BoxAddDrawing(
    uiState: AddDrawingUiState,
    uiAction: (AddDrawingAction) -> Unit,
    onSavingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldForFilling(
            label = stringResource(id = R.string.drawing_name),
            text = uiState.drawingName,
            isValid = (uiState.isValidDrawingName && uiState.isNotRepeatDrawingName),
            onValueChange = { text ->
                uiAction(AddDrawingAction.UpdateDrawingName(text))
            },
            typeOfKeyboard = KeyboardType.Text,
            length = 60,
            lines = 2
        )

        if (!(uiState.isValidDrawingName && uiState.isNotRepeatDrawingName))
            Text(
                text = if (!uiState.isValidDrawingName)
                    stringResource(id = R.string.incorrect_drawing_name)
                else stringResource(id = R.string.repeat_drawing_name),
                fontSize = 10.sp,
                color = Color.Red
            )

        LoadTextField(
            loadFile = { uri ->
                uiAction(AddDrawingAction.UpdateSelectedFile(uri))
            },
            isFilled = uiState.isDrawingFileAttached,
            isValid = uiState.isDrawingFileValid
        )
        if (!uiState.isDrawingFileValid)
            Text(
                text = stringResource(id = R.string.incorrect_drawing_file),
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