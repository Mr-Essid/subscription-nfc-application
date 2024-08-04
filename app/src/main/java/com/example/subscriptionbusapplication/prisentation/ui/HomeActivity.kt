package com.example.subscriptionbusapplication.prisentation.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Instances
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.subscriptionbusapplication.prisentation.ui.theme.SubscriptionBusApplicationTheme
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val deviceId = Settings.Secure.getString(contentResolver, ANDROID_ID)
        val appId = Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)
        setContent {
            val navController = rememberNavController()
            val collectedData = DataFlowRapper(
                appId = deviceId,
                deviceName = appId
            )

            SubscriptionBusApplicationTheme(
                darkTheme = false
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Login,
                    modifier = Modifier
                        .background(
                            appSurfaceColor
                        )
                        .padding(WindowInsets.statusBars.asPaddingValues())
                ) {
                    composable<Login> {
                        LoginScreen(navController = navController)
                    }
                    composable<SignUpFirstStep> {
                        SignUpFirstStepScreen(
                            navController = navController,
                            dataFlowRapper = collectedData
                        )
                    }
                    composable<SignUpSecondStep> {
                        SignUpSecondScreen(
                            dataFlowRapper = collectedData,
                            navController = navController
                        )
                    }
                    composable<SignUpLastStep> {
                        SignUpLastScreen(
                            dataFlowRapper = collectedData,
                            lastStepViewModel = hiltViewModel()
                        )
                    }

                }

            }
        }
    }
}


@Serializable
object Login


@Serializable
object SignUpFirstStep

@Serializable
data class SignUpSecondStep(
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val middleName: String? = null,
)

@Serializable
data class SignUpLastStep(
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val middleName: String?
)


data class DataFlowRapper(
    var firstname: String? = null,
    var lastname: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var password: String? = null,
    var middleName: String? = null,
    var imageUri: Uri? = null,
    val deviceName: String? = null,
    val appId: String? = null
)


fun SignUpFirstStep.toDataFlow(): DataFlowRapper = DataFlowRapper()

fun DataFlowRapper.addFirstStep(signUpSecondStep: SignUpSecondStep) {
    firstname = signUpSecondStep.firstname
    lastname = signUpSecondStep.lastname
    middleName = signUpSecondStep.middleName
    phoneNumber = signUpSecondStep.phoneNumber

}

fun DataFlowRapper.addSecondStep(email: String, password: String) {
    this.email = email
    this.password = password
}

fun DataFlowRapper.addLastStep(imageUri: Uri) {
    this.imageUri = imageUri
}


fun DataFlowRapper.isDataCollected(): Boolean {
    return (
            firstname != null &&
                    lastname != null &&
                    phoneNumber != null &&
                    password != null &&
                    imageUri != null &&
                    email != null
            )

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SubscriptionBusApplicationTheme(
        darkTheme = false
    ) {

    }
}
