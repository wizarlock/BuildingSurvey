package com.example.buildingsurvey.ui.components.workWithDrawing.topAppBar.iconsForTopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun SelectDrawingIcon(
    selectDrawing: (Drawing) -> Unit,
    uiState: WorkWithDrawingUiState
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .clickable(
                    onClick = {
                        if (!uiState.drawingBrokenLine)
                            expanded = !expanded
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.list),
                contentDescription = "List",
                modifier = Modifier.size(32.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 500.dp)
        ) {
            for (drawing in uiState.drawings.collectAsState().value)
                DropdownMenuItem(
                    text = {
                        Text(
                            text = drawing.name,
                            fontSize = 18.sp,
                            style = if (uiState.currentDrawing == drawing) TextStyle(textDecoration = TextDecoration.Underline)
                            else TextStyle(),
                            fontWeight = if (uiState.currentDrawing == drawing) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        if (uiState.currentDrawing != drawing) selectDrawing(drawing)
                        expanded = false
                    }
                )
        }
    }
}