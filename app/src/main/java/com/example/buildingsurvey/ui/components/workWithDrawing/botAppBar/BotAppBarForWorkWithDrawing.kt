package com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDefects.BrokenLineIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDefects.FrameIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDefects.LineSegmentIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDefects.PointDefectIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDefects.SelectDefectIcon
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState

@Composable
fun BotAppBarWorkWithDrawing(
    uiState: WorkWithDrawingUiState,
    updateSelectedTypeOfDefect: (TypeOfDefect) -> Unit,
    addTypeOfDefect: () -> Unit,
    updateTextSelected: () -> Unit,
    updateFrameSelected: () -> Unit,
    updateBrokenLineSelected: () -> Unit,
    updateLineSegmentSelected: () -> Unit,
    updatePointDefectSelected: () -> Unit
) {
    BottomAppBar(
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectSurveyIcon()
                SelectDefectIcon(
                    uiState = uiState,
                    updateSelectedTypeOfDefect = { typeOfDefect ->
                        updateSelectedTypeOfDefect(typeOfDefect)
                    },
                    addTypeOfDefect = { addTypeOfDefect() }
                )
                PointDefectIcon(
                    updatePointDefectSelected = updatePointDefectSelected,
                    uiState = uiState
                )
                LineSegmentIcon(
                    updateLineSegmentSelected = updateLineSegmentSelected,
                    uiState = uiState
                )
                BrokenLineIcon(
                    updateBrokenLineSelected = updateBrokenLineSelected,
                    uiState = uiState
                )
                FrameIcon(
                    updateFrameSelected = updateFrameSelected,
                    uiState = uiState
                )
                TextIcon(
                    updateTextSelected = updateTextSelected,
                    uiState = uiState
                )
            }
        }
    )
}