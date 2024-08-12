package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedEditText
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedPassword
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.title
import com.example.subscriptionbusapplication.prisentation.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    deviceName: String,
    appId: String
) {

    val loginStatus = viewModel.loginStatus
    val mapError = viewModel.errorMap
    val localFocusManager = LocalFocusManager.current

    if (loginStatus.value.isSuccess) {

        navController.navigate(Dashboard()) {
            popUpTo(0)
        }
        viewModel.clearState()

    }

    Scaffold { padding ->

        Box {
            Box(
                Modifier
                    .background(appSurfaceColor)
                    .padding(padding)
                    .padding(horizontal = 8.dp)
                    .imePadding()
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column {
                    Box(
                        modifier = Modifier
                            .layoutId("logo-golder")
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Sign in", style = h2, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.godigitalhr),
                                contentDescription = "go digital logo",
                                modifier = Modifier.width(100.dp),
                                alignment = Alignment.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    AppOutlinedEditText(
                        value = viewModel.email,
                        onChange = { viewModel.email = it },
                        placeholder = "email",
                        errorMessage = mapError["email"]
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AppOutlinedPassword(
                        value = viewModel.password,
                        onChange = { viewModel.password = it },
                        errorMessage = mapError["password"],
                        placeholder = "password"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Text(
                            text = "forget password",
                            style = title.copy(fontSize = 14.sp),
                            modifier = Modifier.clickable { },
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryButton(text = "submit", modifier = Modifier.fillMaxWidth()) {
                        if (viewModel.validateData()) {
                            localFocusManager.clearFocus()
                            viewModel.submitAndListen(deviceName, appId)
                        }
                    }
                    Spacer(modifier = Modifier.height(0.dp))
                    SecondaryButton(text = "cancel", modifier = Modifier.fillMaxWidth()) {
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(thickness = 1.dp, color = appPrimaryColor)
                    Spacer(modifier = Modifier.heightIn(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "haven't account yet", style = bodyText.copy(fontSize = 12.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "create account",
                            style = title.copy(fontSize = 12.sp),
                            modifier = Modifier.clickable {
                                navController.navigate(SignUp)
                            })
                    }

                }


            }

            if (loginStatus.value.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            AnimatedVisibility(
                visible = loginStatus.value.isError,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally()
            ) {
                if (loginStatus.value.isError)
                    ErrorTicketView(
                        message = loginStatus.value.errorMessage ?: "unexpected error happen"
                    ) {
                        viewModel.clearState()
                    }
            }
            if (loginStatus.value.isSuccess) {
                SideEffect {
                    println("we are here and request are done")
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPV() {

}