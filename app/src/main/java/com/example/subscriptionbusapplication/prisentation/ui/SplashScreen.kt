package com.example.subscriptionbusapplication.prisentation.ui

import android.animation.TypeConverter
import android.app.Activity
import android.view.View
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.ErrorTicketView
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreenApp(
    splashScreenViewModel: SplashScreenViewModel,
    navController: NavController

) {

    val checkTokenState = splashScreenViewModel.checkTokenState

    LaunchedEffect(true) {
        delay(1000)
        splashScreenViewModel.checkToken()
    }


    val infiniteSplashScreenTransition =
        rememberInfiniteTransition(label = "app infinite transition")

    val value by infiniteSplashScreenTransition.animateValue(
        initialValue = 170,
        targetValue = 240,
        animationSpec = infiniteRepeatable(
            tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "splash screen",
        typeConverter = Int.VectorConverter
    )

    Scaffold { appPadding ->
        checkTokenState.value.data?.let {
            if (it.isTokenValid == 1) {
                navController.navigate(Dashboard()) {
                    popUpTo(0)
                }
                splashScreenViewModel.eraseState()
            } else {
                navController.navigate(Login) {
                    popUpTo(0)
                }
                splashScreenViewModel.eraseState()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appSurfaceColor)
                .padding(appPadding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagelaunchher),
                contentDescription = "image launcher",
                modifier = Modifier.size(value.dp)
            )
        }

        checkTokenState.value.code?.let {
            if (it >= 0) { // it mean network error or server not respond error in case we have forgot to init the server
                navController.navigate(Login) {
                    popUpTo(0)
                }
                splashScreenViewModel.eraseState()
            } else {
                ErrorTicketView(
                    message = checkTokenState.value.errorMessage ?: "unexpected error just happened"
                ) {
                    splashScreenViewModel.eraseState()
                    splashScreenViewModel.checkToken()
                }
            }
        }
    }
}


@Preview
@Composable
private fun SplashScreenPrev() {
}