package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3

@Composable
fun ProfileField(
    value: String,
    label: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        onValueChange = {},
        textStyle = h3,
        enabled = false,
        leadingIcon = {
            Icon(painter = painter , contentDescription = "icon", modifier = Modifier.size(23.dp))
        },
        colors = TextFieldDefaults.colors(disabledContainerColor = appSurfaceColor, disabledTextColor = Color.Black.copy(alpha = 0.6f)),
        label = {
            Text(text = label)
        },
        modifier = modifier
    )
}


@Preview()
@Composable
private fun ProfileFieldPrev() {
   ProfileField(
       value = "Amine",
       label = "Username",
       painter = painterResource(id = R.drawable.outline_account_circle_24),
       modifier = Modifier.fillMaxWidth()
   )
}