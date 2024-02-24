package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
    line: MutableList<Offset>,
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit
): Modifier {
    val currentDensity = LocalDensity.current
    return when {
        uiState.pointDefectSelected -> {
            Modifier.pointerInput(key1 = 0) {
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

        uiState.lineSegmentSelected -> {
            Modifier.pointerInput(key1 = 1) {
                detectDragGesturesAfterLongPress (
                   onDragStart = { offset ->
                        line.add(offset)
                   },
                   onDrag = { change, _ ->
                       change.consume()
                        when (line.size) {
                            1 -> line.add(change.position)
                          else -> line[1] = change.position
                       }
                    },

                   onDragEnd = {
                        val firstPoint = line.first()
                       val secondPoint = line.last()
                       line.clear()
                       uiAction(
                            WorkWithDrawingAction.AddDefect(
                               points = listOf(firstPoint, secondPoint)
                           )
                        )
                   }
                )
            }
        }
        else -> Modifier
    }
}
