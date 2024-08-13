package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedPassword
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.InfoTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.viewmodel.ChangePasswordViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.DashboardViewModel_HiltModules

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel
) {
    val changePasswordState = changePasswordViewModel.changePasswordState

    var showTicketsMessage by remember {
        mutableStateOf<String?>(null)
    }

    val localFocus = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .background(appSurfaceColor)
            .padding(horizontal = 8.dp)
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(appSurfaceColor)
            ) {
                Surface(onClick = { /*TODO*/ }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(appSurfaceColor)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                            contentDescription = "rest password"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Change Password", style = h3)

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                AppOutlinedPassword(value = changePasswordViewModel.oldPassword, onChange = {
                    changePasswordViewModel.oldPassword = it
                }, placeholder = "old password")
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedPassword(value = changePasswordViewModel.newPassword, onChange = {
                    changePasswordViewModel.newPassword = it
                }, placeholder = "new password")
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedPassword(
                    value = changePasswordViewModel.newPasswordConfirmation, onChange = {
                        changePasswordViewModel.newPasswordConfirmation = it
                    }, placeholder = "confirm password",
                    errorMessage = changePasswordViewModel.errorMap["confirmationError"]
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(text = "submit", modifier = Modifier.fillMaxWidth()) {
                    localFocus.clearFocus()
                    changePasswordViewModel.changePassword()
                }
                SecondaryButton(text = "back", modifier = Modifier.fillMaxWidth()) {
                    localFocus.clearFocus()
                    navController.popBackStack()
                }
            }

        }

        changePasswordState.value.errorMessage?.let {
            ErrorTicketView(message = it) {
                changePasswordViewModel.clearState()
            }
        }

        if (changePasswordState.value.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        changePasswordState.value.data?.let {
            changePasswordViewModel.clearInput()
            showTicketsMessage = it.status
            changePasswordViewModel.clearState()
        }


        showTicketsMessage?.let {
            InfoTicketView(message = it) {
                navController.popBackStack()
            }
        }

    }
}


@Preview
@Composable
private fun ChangePasswordScreenPrev() {
}
