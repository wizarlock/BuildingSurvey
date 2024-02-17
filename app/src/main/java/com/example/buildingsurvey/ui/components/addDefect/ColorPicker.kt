package com.example.buildingsurvey.ui.components.addDefect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.screens.addDefect.actions.AddDefectAction

@Composable
fun ColorPicker(
    color: String,
    isValid: Boolean,
    uiAction: (AddDefectAction) -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = if (color.isEmpty()) stringResource(id = R.string.select_layer_color)
            else stringResource(id = R.string.selected_layer_color),
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .padding(10.dp),
            trailingIcon = {
                if (color.isEmpty()) Icon(painter = painterResource(id = R.drawable.palette), null)
                else Icon(Icons.Rounded.CheckCircle, null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = if (isValid) Color.Black else Color.Red,
                focusedBorderColor = if (isValid) Color.Black else Color.Red,
                unfocusedBorderColor = if (isValid) Color.Black else Color.Red,
            ),
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { showColorPicker = true }
                )
        )
    }
    if (showColorPicker)
        ColorPickerDialog(
            onDismissRequest = { showColorPicker = false },
            onConfirmation = { currentColor ->
                uiAction(AddDefectAction.UpdateDefectColorHexCode(currentColor))
                showColorPicker = false
            }
    )
}