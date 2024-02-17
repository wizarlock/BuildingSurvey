package com.example.buildingsurvey.ui.components.workWithDrawing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.BrokenLineIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.FrameIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.LineSegmentIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.PointDefectIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.SelectDefectIcon
import com.example.buildingsurvey.ui.components.workWithDrawing.iconsForBotBarDefects.TextIcon

@Composable
fun BotAppBarWorkWithDrawing(
        onClick: () -> Unit
) {
    BottomAppBar(
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SelectSurveyIcon()
                SelectDefectIcon(
                    onClick = onClick
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