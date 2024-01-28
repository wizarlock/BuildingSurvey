package com.example.buildingsurvey.ui.components.oftenUsed

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldForFilling(
    label: String,
    text: String,
    isValid: Boolean,
    onValueChange: (String) -> Unit,
    typeOfKeyboard: KeyboardType
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var currentText by remember { mutableStateOf(text) }

    OutlinedTextField(
        modifier = Modifier.padding(10.dp),
        value = currentText.takeIf { it.isNotEmpty() }?.toString() ?: "",
        onValueChange = {
            if (it.length <= 60) {
                currentText = it
                onValueChange(it)
            }
        },
        label = {
            Text(
                text = label,
                color = if (isValid) Color.Black else Color.Red
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = if (isValid) Color.Black else Color.Red,
            focusedBorderColor = if (isValid) Color.Black else Color.Red,
            unfocusedBorderColor = if (isValid) Color.Black else Color.Red,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = typeOfKeyboard,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        visualTransformation =
        if (typeOfKeyboard == KeyboardType.Password) PasswordVisualTransformation()
        else VisualTransformation.None,
        maxLines = 2
    )
}