package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun SelectDefectIcon(
    uiState: WorkWithDrawingUiState,
    updateSelectedTypeOfDefect: (TypeOfDefect) -> Unit,
    addTypeOfDefect: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White)
                .border(2.dp, Color.Black)
                .clickable(onClick = { expanded = !expanded }),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                drawCircle(
                    color = Color(android.graphics.Color.parseColor("#" + uiState.selectedType.hexCode)),
                    radius = size.minDimension / 2f
                )
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension / 2f,
                    style = Stroke(2.dp.toPx())
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 500.dp)
        ) {
            for (type in uiState.typeOfDefect.collectAsState().value)
                DropdownMenuItem(
                    text = {
                        Text(
                            text = type.name,
                            fontSize = 18.sp,
                            style = if (uiState.selectedType == type) TextStyle(textDecoration = TextDecoration.Underline)
                            else TextStyle(),
                            fontWeight = if (uiState.selectedType == type) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(2.dp, Color.Black, CircleShape)
                                .background(
                                    color = Color(android.graphics.Color.parseColor("#" + type.hexCode)),
                                    shape = CircleShape
                                )
                        )
                    },
                    onClick = {
                        if (uiState.selectedType != type) updateSelectedTypeOfDefect(type)
                        expanded = false
                    }
                )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.add_defect),
                        fontSize = 18.sp,
                        style = TextStyle(),
                        fontWeight = FontWeight.Normal
                    )
                },
                trailingIcon =  {
                    Icon(Icons.Rounded.Add, contentDescription = null)
                },
                onClick = {
                    expanded = false
                    addTypeOfDefect()
                }
            )
        }
    }
}
