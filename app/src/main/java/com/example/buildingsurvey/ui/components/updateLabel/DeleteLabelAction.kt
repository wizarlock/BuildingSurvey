package com.example.buildingsurvey.ui.components.updateLabel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R

@Composable
fun DeleteLabelAction(
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(id = R.string.delete_label),
            fontSize = 20.sp,
            modifier = Modifier
                .clickable { onDeleteClick() },
            color = Color.Red,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }
}