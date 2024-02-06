package com.example.buildingsurvey.ui.components.workWithDrawing.iconsForTopBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.data.model.Drawing

@Composable
fun SelectIcon(
    selectDrawing: (Drawing) -> Unit,
    currentDrawing: Drawing,
    listOfDrawings: List<Drawing>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = "Select",
                modifier = Modifier
                    .size(32.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 500.dp)
        ) {
            for (drawing in listOfDrawings)
                DropdownMenuItem(
                    text = {
                        Text(
                            text = drawing.name,
                            fontSize = 18.sp,
                            style = if (currentDrawing == drawing) TextStyle(textDecoration = TextDecoration.Underline)
                            else TextStyle(),
                            fontWeight = if (currentDrawing == drawing) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        if (currentDrawing != drawing) selectDrawing(drawing)
                        expanded = false
                    }
                )
        }
    }
}