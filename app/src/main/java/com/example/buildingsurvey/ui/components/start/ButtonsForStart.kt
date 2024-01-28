package com.example.buildingsurvey.ui.components.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.buildingsurvey.R
import com.example.buildingsurvey.ui.components.oftenUsed.DefaultButton

@Preview
@Composable
fun ButtonsForStart(
    onLogInClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultButton(
            text = stringResource(id = R.string.logIn),
            onLogInClick
        )
    }
}