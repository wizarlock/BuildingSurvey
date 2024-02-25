package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction


@Composable
fun asyncImageModifier(
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit
): Modifier {
    val currentDensity = LocalDensity.current
    val firstPoint = remember { mutableStateOf<Offset?>(null) }
    val lines = remember { mutableStateListOf<Pair<Offset, Offset>>() }

    if (uiState.linesForBrokenLine.isEmpty()) {
        firstPoint.value = null
        lines.clear()
    }

    return when {
        uiState.pointDefectSelected -> {
            Modifier.pointerInput(key1 = 0) {
                detectTapGestures { offset ->
                    val point = with(currentDensity) {
                        Offset(offset.x / density, offset.y / density)
                    }

                    uiAction(
                        WorkWithDrawingAction.AddDefect(
                            isClosed = false,
                            points = listOf(point)
                        )
                    )
                }
            }
        }

        uiState.lineSegmentSelected -> {
            Modifier.pointerInput(key1 = 1) {
                detectTapGestures { offset ->
                    val point = with(currentDensity) {
                        Offset(offset.x / density, offset.y / density)
                    }
                    if (firstPoint.value == null) firstPoint.value = point
                    else {
                        val frPoint = firstPoint.value!!
                        firstPoint.value = null
                        uiAction(
                            WorkWithDrawingAction.AddDefect(
                                isClosed = false,
                                points = listOf(frPoint, point),
                            )
                        )
                    }
                }
            }
        }

        uiState.brokenLineSelected -> {
            Modifier.pointerInput(key1 = 2) {
                detectTapGestures { offset ->
                    val point = with(currentDensity) {
                        Offset(offset.x / density, offset.y / density)
                    }
                    if (firstPoint.value == null) firstPoint.value = point
                    else {
                        val frPoint = firstPoint.value!!
                        firstPoint.value = point
                        lines.add(Pair(frPoint, point))
                        uiAction(WorkWithDrawingAction.UpdateDrawingBrokenLine(isDrawing = true))
                        uiAction(WorkWithDrawingAction.UpdateLinesForBrokenLine(linesForBrokenLine = lines))
                    }
                }
            }
        }

        uiState.frameSelected -> {
            Modifier.pointerInput(key1 = 3) {
                detectTapGestures { offset ->
                    val point = with(currentDensity) {
                        Offset(offset.x / density, offset.y / density)
                    }
                    if (firstPoint.value == null) firstPoint.value = point
                    else {
                        val frPoint = firstPoint.value!!
                        firstPoint.value = null
                        val scPoint = Offset(frPoint.x, point.y)
                        val fourPoint = Offset(point.x, frPoint.y)
                        uiAction(
                            WorkWithDrawingAction.AddDefect(
                                isClosed = true,
                                points = listOf(frPoint, scPoint, point, fourPoint),
                            )
                        )
                    }
                }
            }
        }

        uiState.textSelected -> {
            Modifier.pointerInput(key1 = 4) {
                detectTapGestures { offset ->
                    val point = with(currentDensity) {
                        Offset(offset.x / density, offset.y / density)
                    }

                    uiAction(
                        WorkWithDrawingAction.UpdateCoordinatesOfText(
                            coordinates = Pair(point.x, point.y)
                        )
                    )
                }
            }
        }

        else -> Modifier
    }
}
