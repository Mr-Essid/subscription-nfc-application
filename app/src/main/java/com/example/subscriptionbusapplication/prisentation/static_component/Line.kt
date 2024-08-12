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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4

@Composable
fun LineRepresentation(
    label: String,
    enabled: Boolean = true

) {


    Card(
        enabled = enabled,
        onClick = {},
        modifier = Modifier.width(40.dp),
        colors = CardDefaults.cardColors(containerColor = appPrimaryColor.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp).fillMaxWidth()
        ) {

            Text(text = "L", style = h4.copy(fontSize = 16.sp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, style = h4.copy(fontSize = 16.sp))
        }
    }

}


@Preview(showSystemUi = true)
@Composable
private fun LineRepresentationPrev() {
    LineRepresentation(
        label = "77"
    )
}