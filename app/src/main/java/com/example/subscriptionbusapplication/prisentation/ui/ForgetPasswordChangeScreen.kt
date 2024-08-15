package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedPassword
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.InfoTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels.ForgetPasswordChangeViewModel

@Composable
fun ForgetPasswordChangeScreen(
    viewModel: ForgetPasswordChangeViewModel,
    navController: NavController,
    email: String,
    passport: String
) {

    val focusManager = LocalFocusManager.current
    val errorMapState = viewModel.errorMap
    val changePasswordState = viewModel.changePasswordState

    Scaffold(
        Modifier.background(appSurfaceColor)
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                        contentDescription = "reset password"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Change Password", style = h3)
                }
                Spacer(modifier = Modifier.height(64.dp))
                AppOutlinedPassword(
                    value = viewModel.newPassword,
                    onChange = { viewModel.newPassword = it },
                    placeholder = "new password",
                    errorMessage = errorMapState[ForgetPasswordChangeViewModel.Companion.formData.PASSWORD.name]
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppOutlinedPassword(
                    value = viewModel.passwordConfirmation,
                    onChange = { viewModel.passwordConfirmation = it },
                    placeholder = "password confirmation",
                    errorMessage = errorMapState[ForgetPasswordChangeViewModel.Companion.formData.PASSWORD_CONFIRMATION.name]
                )
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryButton(text = "submit", modifier = Modifier.fillMaxWidth()) {
                    focusManager.clearFocus()
                    viewModel.sendData(email, passport = passport)
                }
                SecondaryButton(text = "cancel", modifier = Modifier.fillMaxWidth()) {
                    focusManager.clearFocus()
                    viewModel.clearState()
                    navController.popBackStack()
                }
            }

            AnimatedVisibility(
                changePasswordState.value.errorMessage != null,
                enter = slideInHorizontally()
            ) {
                changePasswordState.value.errorMessage?.let {
                    ErrorTicketView(message = it) {
                        viewModel.clearState()
                    }
                }
            }

            AnimatedVisibility(changePasswordState.value.data != null) {
                changePasswordState.value.data?.let {
                    InfoTicketView(message = it.status) {
                        viewModel.clearState()
                    }
                }
            }


            changePasswordState.value.data?.let {
                viewModel.clearState()
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "status",
                    "password changed successfully"
                )
                navController.popBackStack()
            }


            if (changePasswordState.value.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }


    }


}


@Preview
@Composable
private fun ForgetPasswordChangeScreenPrev() {
}