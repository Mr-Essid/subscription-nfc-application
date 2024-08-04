package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = appPrimaryColor
        ),
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text)
    }
}


@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {

    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = appPrimaryColor
        ),
        border = BorderStroke(1.dp, appPrimaryColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text)
    }

}

@Preview(showSystemUi = true)
@Composable
private fun ButtonAppPv() {

    Column {

        PrimaryButton(text = "Save Changes") {

        }

        SecondaryButton(text = "Close") {

        }
    }
}