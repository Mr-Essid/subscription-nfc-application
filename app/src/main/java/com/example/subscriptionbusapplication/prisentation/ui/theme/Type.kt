package com.example.subscriptionbusapplication.prisentation.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.subscriptionbusapplication.R

// Set of Material typography styles to start with


val bodyText = TextStyle(
    fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
    fontWeight = FontWeight.W900,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val h1 = bodyText.copy(
    fontSize = 20.sp,
    shadow = Shadow(color = appFooterColor, Offset(0f, 4f), 10f)
)

val h2 = bodyText.copy(
    fontSize = 18.sp
)

val h3 = bodyText.copy(
    fontSize = 16.sp
)

val h4 = bodyText.copy(
    fontSize = 12.sp
)

val title = h2.copy(
    color = appPrimaryColor,
    shadow = Shadow(appFooterColor, Offset(0f, 4f), blurRadius = 2f),
)



@Preview(showSystemUi = true)
@Composable
private fun FontsPref() {
    Column {

    Text(
        "Hello World body text",
        style = bodyText
        )
        Text(
            "Hello World h1",
            style = h1
        )
        Text(
            "Hello World h2",
            style = h2
        )
        Text(
            "Hello World h3",
            style = h3
        )

        Text(text = "Hello World title", style = title)

    }
}



val Typography = Typography(
    titleLarge = h1,
    titleMedium = h2,
    titleSmall = h3,
    headlineSmall = title,
    bodyMedium = bodyText,
    labelSmall = h4

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)