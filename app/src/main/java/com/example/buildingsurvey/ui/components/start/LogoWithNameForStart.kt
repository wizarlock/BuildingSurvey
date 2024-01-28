package com.example.buildingsurvey.ui.components.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R

@Preview
@Composable
fun LogoWithNameForStart() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateAppNameForStart()
        CreateLogoForStart()
    }
}

@Composable
fun CreateAppNameForStart() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateTextForStart(
            text = stringResource(id = R.string.app_name),
            topPadding = 80
        )
        CreateTextForStart(
            text = stringResource(id = R.string.app_definition),
            topPadding = 0
        )
    }
}

@Composable
fun CreateTextForStart(text: String, topPadding: Int) {
    Box(
        modifier = Modifier.padding(top = topPadding.dp)
    ) {
        Text(
            text = text,
            fontSize = 25.sp,
            color = Color.Black
        )
    }
}

@Composable
fun CreateLogoForStart() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.big_logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        )
    }
}