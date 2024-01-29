package com.example.buildingsurvey.ui.components.drawings.drawingsList
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buildingsurvey.R

@Composable
fun BotDrawingBar() {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CreateLogoForBot()
                CreateNameForBot()
            }
        }

    )
}

@Composable
fun CreateLogoForBot() {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.big_logo),
            contentDescription = "logo"
        )
    }
}

@Composable
fun CreateNameForBot() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CreateTextForBot(
            text = stringResource(id = R.string.app_name),
        )
        CreateTextForBot(
            text = stringResource(id = R.string.app_definition),
        )
    }
}

@Composable
fun CreateTextForBot(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = Color.Black
    )
}