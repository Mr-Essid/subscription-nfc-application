package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.subscriptionbusapplication.Constants
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.InfoTicketView
import com.example.subscriptionbusapplication.prisentation.static_component.ProfileField
import com.example.subscriptionbusapplication.prisentation.ui.states.ClientState
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val clientRelatedData = profileViewModel.clientState


    val disConnectState = profileViewModel.logoutState


    var prevStatus by remember {
        mutableStateOf<String?>(null)
    }


    navController.currentBackStackEntry?.savedStateHandle?.get<String>("status")?.let {
        navController.currentBackStackEntry?.savedStateHandle?.remove<String>("status")
        prevStatus = it
    }

    if (disConnectState.value) {
        navController.navigate(Login) {
            profileViewModel.clearState()
            popUpTo(0)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .background(appSurfaceColor)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Surface {
                        Box(
                            Modifier
                                .background(appSurfaceColor)
                                .padding(vertical = 16.dp)
                        ) {
                            Text(text = "Profile", style = h2)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        AsyncImage(
                            model = "${Constants.BASE_URL}${clientRelatedData.imagePath}",
                            contentDescription = "image pic",
                            modifier = Modifier
                                .width(120.dp)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ProfileField(
                        value = clientRelatedData.firstName,
                        label = "Username",
                        painter = painterResource(
                            id = R.drawable.outline_account_circle_24
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ProfileField(
                        value = clientRelatedData.lastName,
                        label = "Lastname",
                        painter = painterResource(
                            id = R.drawable.baseline_person_pin_24
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(8.dp))
                    ProfileField(
                        value = clientRelatedData.email,
                        label = "Email",
                        painter = painterResource(
                            id = R.drawable.baseline_alternate_email_24
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileField(
                        value = clientRelatedData.wallet.toString().split(".")
                            .first() + "." + clientRelatedData.wallet.toString().split(".")
                            .getOrElse(1) { 0 }
                            .toString().take(3),
                        label = "Wallet",
                        painter = painterResource(
                            id = R.drawable.dinartn
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        TextButton(onClick = {
                            navController.navigate(ChangePassword)
                        }) {
                            Text(text = "change password", style = h3)
                        }
                    }
                }

                Column {
                    OutlinedButton(onClick = {
                        profileViewModel.disconnect()
                    }) {
                        Text(
                            "disconnect",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = h3
                        )
                    }
                    OutlinedButton(onClick = { navController.popBackStack(Dashboard(), false) }) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "return back"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("back", style = h3)

                        }
                    }
                }
            }


            prevStatus?.let {
                AnimatedVisibility(visible = true) {
                    InfoTicketView(message = it) {
                        prevStatus = null
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun ProfileScreenPref() {
    val client = ClientState(
        firstName = "firstname",
        lastName = "lastname",
        email = "email@email.com",
        wallet = 300.3,
        imagePath = "image/image.png",
        phoneNumber = "3245242",
        deviceName = "deviceName",
        appId = "AppId"
    )
}