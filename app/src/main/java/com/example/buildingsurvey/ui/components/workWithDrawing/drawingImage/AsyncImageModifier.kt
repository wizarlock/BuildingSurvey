package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun asyncImageModifier(
    lines: MutableList<Pair<Offset, Offset>>,
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit
): Modifier {
    val currentDensity = LocalDensity.current
    return when {
        uiState.lineSegmentSelected -> {
            Modifier.pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        lines.add(Pair(change.position - dragAmount, change.position))
                        if (lines.size > 1) {
                            val firstPoint = lines.first()
                            val lastPoint = lines.last()
                            lines.clear()
                            lines.add(firstPoint)
                            lines.add(lastPoint)
                        }
                    },
                    onDragEnd = {
                        val firstPoint = lines.first().first
                        val secondPoint = lines.last().second
                        lines.clear()
                        uiAction(
                            WorkWithDrawingAction.AddDefect(
                                points = listOf(firstPoint, secondPoint)
                            )
                        )
                    }
                )
            }
        }

        uiState.pointDefectSelected -> {
            Modifier.pointerInput(Unit) {
                detectTapGestures { offset ->
                    val point = with(currentDensity) {
                        Offset(offset.x / density, offset.y / density)
                    }
                    uiAction(
                        WorkWithDrawingAction.AddDefect(
                            points = listOf(point)
                        )
                    )
                }
            }
        }

        uiState.brokenLineSelected -> {
            Modifier.pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        uiAction(WorkWithDrawingAction.UpdateDrawingBrokenLine)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        lines.add(Pair(change.position - dragAmount, change.position))
                        if (lines.size > 1) {
                            val firstPoint = lines.first()
                            val lastPoint = lines.last()
                            lines.clear()
                            lines.add(firstPoint)
                            lines.add(lastPoint)
                        }
                    },
                    onDragEnd = {

                    }
                )
            }
        }

        else -> Modifier
    }
}