package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    Box(
        modifier = boxForAllModifier(
            swipeMode = uiState.swipeMode,
            uiAction = uiAction
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = drawingImageModifier(
                photoMode = uiState.photoMode,
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
                modifier = Modifier
                    .align(Alignment.Center),
                contentDescription = null
            )

            uiState.labels.collectAsState().value.forEach { label ->
                Box(
                    modifier = labelModifier(
                        photoMode = uiState.photoMode,
                        swipeMode = uiState.swipeMode,
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
        }
    }
}

private fun takeLastDigits(name: String): String {
    return if (name.length > 1) name.takeLast(2)
    else "0$name"
}