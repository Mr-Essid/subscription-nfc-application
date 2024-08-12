package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import java.util.Locale


@Composable
fun DayRepresentation(
    label: String,
    enabled: Boolean = true

) {


    Card(
        enabled = enabled,
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(48.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .fillMaxWidth()
        ) {
            Text(text = label.uppercase(), style = h4.copy(fontSize = 12.sp))
        }
    }

}


@Preview(showSystemUi = true)
@Composable
private fun DayRepresentationPrev() {
    DayRepresentation(
        enabled = false,
        label = "Mon".capitalize(Locale.ROOT)
    )
}