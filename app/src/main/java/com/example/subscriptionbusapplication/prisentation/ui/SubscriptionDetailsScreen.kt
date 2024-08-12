package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.prisentation.static_component.AlertConfirmationApp
import com.example.subscriptionbusapplication.prisentation.static_component.AlertState
import com.example.subscriptionbusapplication.prisentation.static_component.AppAlert
import com.example.subscriptionbusapplication.prisentation.static_component.DayRepresentation
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.LineRepresentation
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import com.example.subscriptionbusapplication.prisentation.viewmodel.SubscriptionDetailsViewModel
import java.util.Locale

@Composable
fun SubscriptionDetailsScreen(
    viewModel: SubscriptionDetailsViewModel,
    navController: NavController
) {


    val loadSubscriptionAllDetailsDetailsState = viewModel.loadCurrentSubscriptionState

    val subscribe = viewModel.subscribeStat

    var buttonIsDisabled by remember {
        mutableStateOf(false)
    }

    var isDialogShown by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = "Subscription Details",
            style = h2
        )

        loadSubscriptionAllDetailsDetailsState.value.data?.let { currentSubscription ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "general information", color = appPrimaryColor, style = h3)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "months period icon",
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "${currentSubscription.months} Mth", style = h3)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mapicon),
                        contentDescription = "months period icon",
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentSubscription.zoneName.replaceFirstChar { it.uppercase() },
                        style = h3
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dinartn),
                        contentDescription = "months period icon",
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "${currentSubscription.price} DT", style = h3)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "lines", style = h3, color = appPrimaryColor)

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                if (currentSubscription.lines.isNotEmpty())
                    currentSubscription.lines.map {
                        Spacer(modifier = Modifier.width(8.dp))
                        LineRepresentation(label = it.label)
                    }
                else
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Lines Available Right Now",
                            style = h4,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "day", style = h3, color = appPrimaryColor)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                currentSubscription.days.map {
                    DayRepresentation(label = it.shortName, enabled = it.isAvailableRightNow != 0)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                enabled = viewModel.currentUserCanSubscribe && !buttonIsDisabled,
                onClick = {
                    isDialogShown = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = appPrimaryColor)

            )
            {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center
                ) {
                    Icon(painter = painterResource(id = R.drawable.label), contentDescription = "t")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "subscribe")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { navController.popBackStack() },
                Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = appSurfaceColor)

            )
            {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "t",
                        tint = appPrimaryColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "back", color = appPrimaryColor)
                }
            }
        }
    }


    if (loadSubscriptionAllDetailsDetailsState.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }



    if (subscribe.value.isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }

    if (subscribe.value.isSuccess) {
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "id-subscription",
            subscribe.value.data!!.subscriptionXId
        )
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "wallet",
            subscribe.value.data!!.currentWallet
        )

        navController.popBackStack(Dashboard(), false)
    }


    AnimatedVisibility(
        visible = subscribe.value.isError,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally()
    ) {
        ErrorTicketView(message = subscribe.value.errorMessage ?: "unexpected error just done") {
            viewModel.clearState()
            buttonIsDisabled = false
        }
    }



    if (isDialogShown) {
        AppAlert(
            title = "Subscription Confirmation",
            bodyText = "are you sure you wanna subscribe to this plan",
            state = AlertState.ALERT_INFO,
            onConfirmState = {
                isDialogShown = false
                buttonIsDisabled = true
                viewModel.subscribe()
            },
            onDismiss = {
                buttonIsDisabled = false
                isDialogShown = false
            }
        )
    }
}


@Preview(showSystemUi = true)
@Composable
private fun SubscriptionDetailsPrev() {
}