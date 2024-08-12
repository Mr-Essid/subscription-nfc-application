package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.subscriptionbusapplication.Constants
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.data.models.SubscribeReturnModel
import com.example.subscriptionbusapplication.prisentation.static_component.ActiveSubscription
import com.example.subscriptionbusapplication.prisentation.static_component.SubscriptionDetailsCard
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import com.example.subscriptionbusapplication.prisentation.viewmodel.DashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController,
    subscriptionReturnModel: SubscribeReturnModel? = null
) {

    var isActiveSessionExpended by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = subscriptionReturnModel) {
        if (subscriptionReturnModel != null) {
            viewModel.updateCurrentClientWallet(subscriptionReturnModel.currentWallet)
            viewModel.loadSubscriptionXById(subscriptionReturnModel.subscriptionXId)
        }
    }

    val clientState = viewModel.clientSate
    val clientListSubscription = viewModel.currentListOfSubscriptions


    Scaffold {
        Box(
            modifier = Modifier
                .background(appSurfaceColor)
                .padding(it)
                .fillMaxSize()

        ) {
            Column(
                Modifier.padding(horizontal = 8.dp)
            ) {
                Surface(
                    modifier = Modifier.background(appSurfaceColor),
                    tonalElevation = 18.dp,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(appSurfaceColor)
                            .padding(vertical = 14.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.godigitalhr),
                            contentDescription = "log of application",
                            Modifier.width(82.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.outline_account_circle_24),
                            contentDescription = "account info"
                        )
                    }

                }
                clientState.value?.let { currentUser ->

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


                        Column {
                            Text(
                                text = "Welcome ${currentUser.firstName}",
                                style = h3
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = LocalDate.now()
                                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                style = h4
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier.layoutId("personnel information")
                        ) {
                            Text(
                                text = "personnel information",
                                style = h3,
                                color = appPrimaryColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)

                            ) {
                                println("${Constants.BASE_URL}${currentUser.imagePath}")
                                AsyncImage(
                                    model = "${Constants.BASE_URL}${currentUser.imagePath}",
                                    contentDescription = "person photo",
                                    contentScale = ContentScale.Fit,
                                    colorFilter = ColorFilter.tint(
                                        color = Color.White.copy(alpha = 0.2f),
                                        blendMode = BlendMode.Lighten
                                    ),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .size(120.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Column(Modifier.padding(vertical = 8.dp)) {
                                    Text(
                                        text = "Name: ${
                                            currentUser.firstName.capitalize(
                                                Locale.ROOT
                                            )
                                        }",
                                        style = h4,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Lastname: ${
                                            currentUser.lastName.capitalize(
                                                Locale.ROOT
                                            )
                                        }",
                                        style = h4,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Wallet: ${
                                            currentUser.wallet.toString().capitalize(
                                                Locale.ROOT
                                            )
                                        } dt",
                                        style = h4,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            val transition = animateIntAsState(
                                targetValue = if (isActiveSessionExpended) clientListSubscription.size else 3,
                                label = "expend active sessions"
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "active subscriptions",
                                    style = h3,
                                    color = appPrimaryColor
                                )

                                if (clientListSubscription.size > 3)
                                    IconButton(onClick = {
                                        isActiveSessionExpended = !isActiveSessionExpended
                                    }) {
                                        Icon(
                                            painter = painterResource(id = if (!isActiveSessionExpended) R.drawable.baseline_arrow_drop_down_24 else R.drawable.baseline_arrow_drop_up_24),
                                            contentDescription = "expend, shrink active subscriptions"
                                        )
                                    }
                            }

                            if (clientListSubscription.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not Active Subscriptions yet", style = h3)
                                }
                            } else

                                for (subscriptionX in clientListSubscription.take(transition.value)) {
                                    ActiveSubscription(subscriptionX = subscriptionX)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                        }


                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "available subscriptions",
                            style = h3,
                            color = appPrimaryColor.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Available Zone", style = h4)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.mapicon),
                                contentDescription = "map",
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        if (viewModel.mapOfZones.isNotEmpty()) {
                            viewModel.mapOfZones.keys.forEach { zoneName ->
                                Text(
                                    text = "Zone ${zoneName.capitalize(Locale.ROOT)}",
                                    style = bodyText.copy(fontSize = 16.sp)
                                )
                                Spacer(modifier = Modifier.height(height = 4.dp))
                                Row(
                                    modifier = Modifier
                                        .horizontalScroll(rememberScrollState())
                                        .padding(horizontal = 4.dp, vertical = 8.dp)
                                ) {
                                    viewModel.mapOfZones[zoneName]?.forEach { subscription ->
                                        SubscriptionDetailsCard(subscriptionDetails = subscription) {
                                            navController.navigate(
                                                SubscriptionDetails(
                                                    subscription.id,
                                                    currentUser.wallet >= subscription.price
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }

                        }



                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Available Labels", style = h4)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.label),
                                contentDescription = "map",
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        if (viewModel.mapOfSubscriptionDetails.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            viewModel.mapOfSubscriptionDetails.keys.forEach { month ->
                                Text(
                                    text = "Subscription ${month.capitalize(Locale.ROOT)}",
                                    style = bodyText.copy(fontSize = 16.sp)
                                )
                                Spacer(modifier = Modifier.height(height = 4.dp))
                                Row(
                                    modifier = Modifier
                                        .horizontalScroll(rememberScrollState())
                                        .padding(horizontal = 4.dp, vertical = 8.dp)
                                ) {
                                    viewModel.mapOfSubscriptionDetails[month]?.forEach { subscription ->
                                        SubscriptionDetailsCard(subscriptionDetails = subscription) {
                                            navController.navigate(
                                                SubscriptionDetails(
                                                    subscription.id,
                                                    currentUser.wallet >= subscription.price
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }


                        }
                    }


                    // user subscription screen

                    // available subscriptions

                    // footer
                }
                if (viewModel.currentClientLoadState.value.isLoading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}


@Preview
@Composable
private fun MainDashboard() {
}