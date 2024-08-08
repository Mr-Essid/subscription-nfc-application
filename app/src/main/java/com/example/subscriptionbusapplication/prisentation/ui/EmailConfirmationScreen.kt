package com.example.subscriptionbusapplication.prisentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.prisentation.static_component.ExpendableContent
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun EmailConfirmationScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {


    var benefitOne by remember {
        mutableStateOf(false)
    }
    var benefitTwo by remember {
        mutableStateOf(false)
    }

    var benefitThree by remember {
        mutableStateOf(false)
    }

    var benefitFour by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Scaffold { scaffoldPadding ->

        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {

            Column {
                Text(text = "Thanks for your registration", style = h2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "right now your are of the people who are benefit of our subscription system.",
                    style = bodyText.copy(fontSize = 12.sp),
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                ExpendableContent(
                    title = "Easy To Apply",
                    textBody = "With our System you do not need to pay with your packet money inside our bus lines",
                    currentState = benefitOne
                ) {
                    benefitTwo = false
                    benefitThree = false
                    benefitFour = false
                    benefitOne = it
                }

                ExpendableContent(
                    title = "Save Time",
                    textBody = "The only time you spent only to register to our system after that your subscription within single click done",
                    currentState = benefitTwo
                ) {
                    benefitThree = false
                    benefitFour = false
                    benefitOne = false
                    benefitTwo = it
                }
                ExpendableContent(
                    title = "Quick Get In",
                    textBody = "using our modern tech you only need the phone, to get into our lines",
                    currentState = benefitThree
                ) {
                    benefitFour = false
                    benefitOne = false
                    benefitTwo = false
                    benefitThree = it
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "get all those benefits and more once you get confirmed your account",
                    style = bodyText.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PrimaryButton(text = "Go to mailer") {
                        val intent = Intent(Intent.ACTION_MAIN).also {
                            it.addCategory(Intent.CATEGORY_APP_EMAIL)
                            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }

                        val chooser = Intent.createChooser(intent, "open email client")

                        if (chooser.resolveActivity(context.packageManager) != null) {
                            (context as Activity).startActivity(chooser)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    SecondaryButton(text = "Back to login") {
                        navController?.popBackStack(Login, false)
                    }
                }

            }


        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun EmailConfirmationScreenPrev() {

    EmailConfirmationScreen()

}