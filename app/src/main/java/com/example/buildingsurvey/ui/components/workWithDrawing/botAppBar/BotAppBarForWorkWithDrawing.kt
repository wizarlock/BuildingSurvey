package com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.buildingsurvey.R
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.components.oftenUsed.TextFieldForFilling
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDecisionMaking.AcceptIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDecisionMaking.ConnectToBegin
import com.example.buildingsurvey.ui.components.workWithDrawing.botAppBar.iconsForBotBarDecisionMaking.DeclineIcon
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
    updatePointDefectSelected: () -> Unit,
    acceptForBrokenLine: () -> Unit,
    declineForBrokenLine: () -> Unit,
    connect: () -> Unit,
    acceptForText: () -> Unit,
    declineForText: () -> Unit,
    textChange: (String) -> Unit
) {
    BottomAppBar(
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when {
                    uiState.drawingBrokenLine -> {
                        DeclineIcon(
                            decline = { declineForBrokenLine() },
                        )
                        ConnectToBegin(
                            connect = { connect() }
                        )
                        AcceptIcon(
                            accept = { acceptForBrokenLine() }
                        )
                    }

                    uiState.coordinatesOfText.first != -1f && uiState.coordinatesOfText.second != -1f -> {
                        DeclineIcon(
                            decline = { declineForText() },
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            TextFieldForFilling(
                                label = stringResource(id = R.string.text),
                                text = uiState.text,
                                isValid = uiState.isValidText,
                                onValueChange = { text ->
                                    textChange(text)
                                },
                                typeOfKeyboard = KeyboardType.Text,
                                length = 60,
                                lines = 1
                            )

                        }

                        AcceptIcon(
                            accept = { acceptForText() }
                        )
                    }

                    else -> {
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
            }
        }
    )
}