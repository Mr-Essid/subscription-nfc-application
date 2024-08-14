package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.R.*
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h1
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import kotlinx.coroutines.delay

@Composable
fun ForgetPasswordTryCodeScreen(

    email: String
) {

    var isEnable by rememberSaveable {
        mutableStateOf(false)
    }

    var timer by rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = isEnable) {

        if (!isEnable)
            while (true) {
                delay(1000)
                timer += 1
            }
    }


    if (timer >= 60) {
        isEnable = true
        timer = 0
    }

    Scaffold(
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = drawable.baseline_restart_alt_24),
                        contentDescription = "code recovery"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Code", style = h2)
                }

                Spacer(modifier = Modifier.height(64.dp))

                var myTextFieldValue by remember {
                    mutableStateOf("124")
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = myTextFieldValue,
                        onValueChange = {
                            if (it.length <= 4)
                                myTextFieldValue = it
                        },
                        textStyle = TextStyle(color = Color.Transparent, fontSize = 0.sp),
                        cursorBrush = SolidColor(Color.Black),
                        decorationBox = { innertext ->

                            Row {
                                repeat(4) { index ->
                                    Box(
                                        Modifier
                                            .padding(horizontal = 4.dp)
                                            .width(28.dp)
                                            .height(42.dp)
                                            .border(
                                                2.dp,
                                                color = appPrimaryColor,
                                                shape = RoundedCornerShape(4.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (myTextFieldValue.length > index)
                                            Text(
                                                text = myTextFieldValue.getOrNull(index).toString(),
                                                style = h1,
                                                color = appPrimaryColor.copy(alpha = 0.7f)
                                            )
                                        else {
                                            if (myTextFieldValue.length == index) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(9.dp))
                                                        .size(18.dp)
                                                        .border(1.dp, color = Color.Black)
                                                )
                                                innertext()
                                            } else {

                                                Text(
                                                    text = " ",
                                                    style = h1,
                                                    color = appPrimaryColor.copy(alpha = 0.7f)
                                                )
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    Button(
                        onClick = {
                            isEnable = false
                        },
                        enabled = isEnable,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = appPrimaryColor
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                            contentDescription = "resend"
                        )
                        Text(
                            text = if (!isEnable) timer.toString() else "resend",
                            modifier = Modifier.width(if (!isEnable) 28.dp else 60.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PrimaryButton(text = "submit", modifier = Modifier.width(120.dp)) {
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    SecondaryButton(text = "cancel", modifier = Modifier.width(120.dp)) {

                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = buildAnnotatedString {

                    pushStyle(SpanStyle(fontWeight = FontWeight.ExtraBold, color = appPrimaryColor))
                    append("Note: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Normal, color = Color.Black))
                    append("code has been sent to your email, check your email")
                })
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ForgetPasswordTryCodeScreenPrev() {


    ForgetPasswordTryCodeScreen()

}