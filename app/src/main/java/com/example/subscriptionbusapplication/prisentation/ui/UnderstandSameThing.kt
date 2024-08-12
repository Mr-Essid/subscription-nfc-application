package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun UnderstandSameThingV1(modifier: Modifier = Modifier) {

        Column(
            Modifier.padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text("example",)
            Text("Hello World2")
        }

        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
}


@Preview(showSystemUi = true)
@Composable
private fun UnderstandSameThingVersion1Prev() {
    UnderstandSameThingV1()
}