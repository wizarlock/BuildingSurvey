package com.example.buildingsurvey.ui.components.drawings.drawingsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.model.Drawing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDrawing(
    list: List<Drawing>,
    onDrawingClick: (Drawing) -> Unit,
    onCloseClick: (Drawing) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = if (list.isEmpty()) stringResource(id = R.string.drawings_empty) else stringResource(
                    id = R.string.select_drawing
                ),
                onValueChange = { },
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded && list.isNotEmpty(),
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { entry ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onDrawingClick(entry)
                        },
                        text = {
                            Text(
                                text = entry.name,
                                fontSize = 18.sp
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    onCloseClick(entry)
                                    if (list.isEmpty()) expanded = false
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}