package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawingImage(
    uiState: WorkWithDrawingUiState,
    uiAction: (WorkWithDrawingAction) -> Unit,
    onLabelClick: (Label) -> Unit
) {
    val defectsList = uiState.defectsList.collectAsState().value
    val defectPoints = uiState.defectPointsList.collectAsState().value
    val textList = uiState.texts.collectAsState().value
    val density = LocalDensity.current

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
                    uiState = uiState,
                    uiAction = uiAction
                )
                    .align(Alignment.Center)
                    .onGloballyPositioned { coordinates ->
                        val widthDp = with(density) { coordinates.size.width.toDp() }
                        val heightDp = with(density) { coordinates.size.height.toDp() }
                        uiAction(
                            WorkWithDrawingAction.LoadAppWidthAndHeight(
                                widthDp.value,
                                heightDp.value
                            )
                        )
                    },

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

            uiState.texts.collectAsState().value.forEach { text ->
                Box(
                    modifier = Modifier
                        .absoluteOffset(text.xInApp.dp, text.yInApp.dp)
                        .widthIn(max = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text (
                        text = text.text,
                        fontSize = 2.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 1.5.sp
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
                                color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                                style = Stroke(width = 0.5.dp.toPx()),
                                center = Offset(
                                    (pointsOfDefect.first().xInApp.dp).toPx(),
                                    (pointsOfDefect.first().yInApp.dp).toPx()
                                ),
                                radius = 1.75.dp.toPx()
                            )

                            drawCircle(
                                color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                                radius = 0.6.dp.toPx(),
                                center = Offset(
                                    (pointsOfDefect.first().xInApp.dp).toPx(),
                                    (pointsOfDefect.first().yInApp.dp).toPx()
                                ),
                            )

                        }

                        2 -> drawLine(
                            color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                            start = Offset(
                                (pointsOfDefect.first().xInApp.dp).toPx(),
                                (pointsOfDefect.first().yInApp.dp).toPx()
                            ),
                            end = Offset(
                                (pointsOfDefect.last().xInApp.dp).toPx(),
                                (pointsOfDefect.last().yInApp.dp).toPx()
                            ),
                            strokeWidth = 2f
                        )

                        0 -> {}
                        else -> {
                            for (index in 0..pointsOfDefect.size - 2)
                                drawLine(
                                    color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                                    start = Offset(
                                        (pointsOfDefect[index].xInApp.dp).toPx(),
                                        (pointsOfDefect[index].yInApp.dp).toPx()
                                    ),
                                    end = Offset(
                                        (pointsOfDefect[index + 1].xInApp.dp).toPx(),
                                        (pointsOfDefect[index + 1].yInApp.dp).toPx()
                                    ),
                                    strokeWidth = 2f
                                )
                            if (defect.isClosed)
                                drawLine(
                                    color = Color(android.graphics.Color.parseColor(defect.hexCode)),
                                    start = Offset(
                                        (pointsOfDefect.last().xInApp.dp).toPx(),
                                        (pointsOfDefect.last().yInApp.dp).toPx()
                                    ),
                                    end = Offset(
                                        (pointsOfDefect.first().xInApp.dp).toPx(),
                                        (pointsOfDefect.first().yInApp.dp).toPx()
                                    ),
                                    strokeWidth = 2f
                                )
                        }
                    }
                }

                if (uiState.brokenLineSelected)
                    uiState.linesForBrokenLine.forEach { line ->
                        drawLine(
                            color = Color(android.graphics.Color.parseColor(uiState.selectedType.hexCode)),
                            start = Offset(
                                (line.first.x.dp).toPx(),
                                (line.first.y.dp).toPx()
                            ),
                            end =  Offset(
                                (line.second.x.dp).toPx(),
                                (line.second.y.dp).toPx()
                            ),
                            strokeWidth = 2f
                        )
                    }
            }
        }
    }
}


private fun takeLastDigits(name: String): String {
    return if (name.length > 1) name.takeLast(2)
    else "0$name"
}
