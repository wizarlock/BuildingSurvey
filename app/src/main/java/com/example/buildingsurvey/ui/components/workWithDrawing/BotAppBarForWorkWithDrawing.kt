package com.example.buildingsurvey.ui.components.workWithDrawing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.BrokenLineIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.FrameIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.LineSegmentIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.PointDefectIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.SelectDefectIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.TextIcon
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun BotAppBarWorkWithDrawing(
    uiState: WorkWithDrawingUiState,
    updateSelectedTypeOfDefect: (TypeOfDefect) -> Unit,
    addTypeOfDefect: () -> Unit
) {
    BottomAppBar(
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SelectSurveyIcon()
                SelectDefectIcon(
                    uiState = uiState,
                    updateSelectedTypeOfDefect = { typeOfDefect ->
                        updateSelectedTypeOfDefect(typeOfDefect)
                    },
                    addTypeOfDefect = { addTypeOfDefect() }
                )
                PointDefectIcon()
                LineSegmentIcon()
                BrokenLineIcon()
                FrameIcon()
                TextIcon()
            }
        }
    )
}