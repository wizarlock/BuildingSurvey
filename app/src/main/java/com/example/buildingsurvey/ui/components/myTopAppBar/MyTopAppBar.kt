package com.example.buildingsurvey.ui.components.myTopAppBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buildingsurvey.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onSettingsClick: () -> Unit, onAvatarClick: () -> Unit) {
    TopAppBar(
        title = {},
        actions = {
            AvatarIcon(
                painter = painterResource(id = R.drawable.avatar_ex),
                onClick = {
                    onAvatarClick()
                }
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onSettingsClick() }
            ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    )
}

@Composable
fun AvatarIcon(
    painter: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(Color.Black, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable(onClick = onClick)
        )
    }
}