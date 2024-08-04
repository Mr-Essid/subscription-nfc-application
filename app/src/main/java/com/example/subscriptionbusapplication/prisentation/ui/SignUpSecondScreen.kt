package com.example.subscriptionbusapplication.prisentation.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedEditText
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedPassword
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.title
import com.example.subscriptionbusapplication.prisentation.viewmodel.SignUpSecondStepViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpSecondScreen(
    viewModel: SignUpSecondStepViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController,
    dataFlowRapper: DataFlowRapper? = null
) {
    val errorMap = viewModel.mapError.observeAsState()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val currentFocus = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        Box(
            Modifier
                .background(appSurfaceColor)
                .padding(padding)
                .padding(horizontal = 8.dp)
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
                        Text(text = "Sign up", style = h2, textAlign = TextAlign.Center)
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
                Text(text = "account information", style = h3)
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedEditText(
                    value = viewModel.email,
                    onChange = {
                        viewModel.email = it
                    },
                    placeholder = "email",
                    errorMessage = errorMap.value?.get("email")
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedPassword(
                    value = viewModel.password,
                    onChange = {
                        viewModel.password = it
                    },
                    placeholder = "password",
                    errorMessage = errorMap.value?.get("password")
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedPassword(
                    value = viewModel.confirmationPassword,
                    onChange = {
                        viewModel.confirmationPassword = it
                    },
                    placeholder = "confirm password",
                    errorMessage = errorMap.value?.get("passwordConfirmation")
                )

                Spacer(modifier = Modifier.height(8.dp))
                PrimaryButton(text = "sign up", modifier = Modifier.fillMaxWidth()) {
                    currentFocus.clearFocus()
                    if (viewModel.validateData()) {
                        dataFlowRapper?.let {
                            it.addSecondStep(viewModel.email, viewModel.password)

                        }
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
                    Text(text = "already have account", style = bodyText.copy(fontSize = 12.sp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "login account",
                        style = title.copy(fontSize = 12.sp),
                        modifier = Modifier.clickable {
                            navController.popBackStack(Login, false)
                        })
                }

            }

        }
    }

}


@Preview(showSystemUi = true)
@Composable
private fun SeConnect() {

}