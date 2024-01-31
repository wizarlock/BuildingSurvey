package com.example.buildingsurvey.ui.components.drawings.addDrawing

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R

@Composable
fun LoadTextField(
    loadFile: (Uri?) -> Unit,
    isFilled: Boolean,
    isValid: Boolean
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            loadFile(uri)
        }

    Box {
        OutlinedTextField(
            value = if (isFilled) stringResource(id = R.string.loaded_drawing) else stringResource(
                id = R.string.attach_drawing
            ),
            onValueChange = { },
            modifier = Modifier
                .padding(10.dp),
            trailingIcon = {
                if (isFilled) {
                    Icon(Icons.Rounded.CheckCircle, null)
                } else {
                    Icon(Icons.Rounded.Add, null)
                }
            },
            readOnly = true,
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
                    onClick = {
                        launcher.launch("application/pdf")
                    }
                )
        )
    }
}