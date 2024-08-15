package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedEditText
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.InfoTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels.ForgetPasswordRequestViewModel

@Composable
fun ForgetPasswordRequestScreen(
    navController: NavController,
    viewModel: ForgetPasswordRequestViewModel
) {


    var emailState by remember {
        mutableStateOf("")
    }

    val localFocus = LocalFocusManager.current
    val requestState = viewModel.emailRequestState

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .background(appSurfaceColor)
                .padding(paddingValues)
                .fillMaxSize()
        )
        {


            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        contentDescription = "rest password builder",
                        painter = painterResource(id = R.drawable.baseline_restart_alt_24)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Forget password", style = h3)
                }
                Column(
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.height(128.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_alternate_email_24),
                            contentDescription = "email address"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Email", style = h3)
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    AppOutlinedEditText(
                        value = viewModel.emailValue,
                        onChange = { viewModel.emailValue = it },
                        placeholder = "email",
                        errorMessage = viewModel.mapError["email"]
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,

                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PrimaryButton(text = "search", modifier = Modifier.width(120.dp)) {
                            localFocus.clearFocus()
                            viewModel.submitRequest()
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        SecondaryButton(text = "back", modifier = Modifier.width(120.dp)) {
                            viewModel.clearState()
                            navController.popBackStack()
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "change your password by email",
                        modifier = Modifier.fillMaxWidth(),
                        style = h4
                    )

                    Spacer(
                        modifier = Modifier
                            .height(48.dp)
                            .imePadding()
                    )
                }
            }




            if (requestState.value.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }



            AnimatedVisibility(
                visible = requestState.value.errorMessage != null,
                enter = slideInHorizontally()
            ) {

                requestState.value.errorMessage?.let {
                    ErrorTicketView(message = it) {
                        viewModel.clearState()
                    }
                }
            }

            requestState.value.data?.let {
                viewModel.clearState()
                // it mean that the request done
                navController.popBackStack()

                navController.navigate(ForgetPasswordTryCode(email = viewModel.emailValue))

            }

        }


    }

}


@Preview
@Composable
private fun ForgetPasswordRequestScreenPrev() {

}