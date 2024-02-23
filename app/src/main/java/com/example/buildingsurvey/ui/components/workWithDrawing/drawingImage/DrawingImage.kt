package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun DrawingImage(
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit,
    onLabelClick: (Label) -> Unit
) {
    val linesSegment = remember { mutableStateListOf<Pair<Offset, Offset>>() }
    val defectsList = uiState.defectsList.collectAsState().value
    val defectPoints = uiState.defectPointsList.collectAsState().value

    Box(
        modifier = boxForAllModifier(
            uiState = uiState,
            uiAction = uiAction
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = boxForScaleModifier(
                uiState = uiState,
                uiAction = uiAction
            )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        uiState.currentDrawing.drawingFilePath
                    )
                    .size(Size.ORIGINAL)
                    .build(),
                modifier = asyncImageModifier(
                    lines = linesSegment,
                    uiState = uiState,
                    uiAction = uiAction
                )
                    .align(Alignment.Center),

                contentDescription = null
            )

            uiState.labels.collectAsState().value.forEach { label ->
                Box(
                    modifier = labelModifier(
                        uiState = uiState,
                        label = label,
                        onLabelClick = { entry ->
                            onLabelClick(entry)
                        },
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = takeLastDigits(label.name),
                        fontSize = 2.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Canvas(
                modifier = Modifier
            ) {
                defectsList.forEach { defect ->
                    val pointsOfDefect =
                        defectPoints
                            .filter { it.defectId == defect.id }
                            .sortedBy { it.position }
                    when (pointsOfDefect.size) {
                        1 -> {
                            drawCircle(
                                color = Color.Black,
                                radius = 2.dp.toPx(),
                                center = Offset(
                                    (pointsOfDefect.first().xInApp.dp).toPx(),
                                    (pointsOfDefect.first().yInApp.dp).toPx()
                                )
                            )
                        }

                        2 -> drawLine(
                            color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                            start = Offset(
                                pointsOfDefect.first().xInApp,
                                pointsOfDefect.first().yInApp
                            ),
                            end = Offset(
                                pointsOfDefect.last().xInApp,
                                pointsOfDefect.last().yInApp
                            ),
                            strokeWidth = 2f
                        )

                        0 -> {}
                        else ->
                            for (index in pointsOfDefect.indices)
                                drawLine(
                                    color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                                    start = Offset(
                                        pointsOfDefect[index].xInApp,
                                        pointsOfDefect[index].yInApp
                                    ),
                                    end = Offset(
                                        pointsOfDefect[index + 1].xInApp,
                                        pointsOfDefect[index + 1].yInApp
                                    ),
                                    strokeWidth = 2f
                                )
                    }
                }

                if (uiState.lineSegmentSelected && linesSegment.size == 2)
                    drawLine(
                        color = Color(android.graphics.Color.parseColor(uiState.selectedType.hexCode)),
                        start = linesSegment.first().first,
                        end = linesSegment.last().second,
                        strokeWidth = 2f
                    )

                if (uiState.brokenLineSelected && linesSegment.size == 2)
                    drawLine(
                        color = Color(android.graphics.Color.parseColor(uiState.selectedType.hexCode)),
                        start = linesSegment.first().first,
                        end = linesSegment.last().second,
                        strokeWidth = 2f
                    )
            }
        }
    }
}


private fun takeLastDigits(name: String): String {
    return if (name.length > 1) name.takeLast(2)
    else "0$name"
}