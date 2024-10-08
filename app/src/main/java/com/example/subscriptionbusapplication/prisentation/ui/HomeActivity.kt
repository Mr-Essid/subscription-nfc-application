package com.example.subscriptionbusapplication.prisentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.models.Passport
import com.example.subscriptionbusapplication.data.models.SubscribeResult
import com.example.subscriptionbusapplication.data.models.SubscribeReturnModel
import com.example.subscriptionbusapplication.helpers.CustomNavTypes
import com.example.subscriptionbusapplication.prisentation.ui.states.ClientState
import com.example.subscriptionbusapplication.prisentation.ui.theme.SubscriptionBusApplicationTheme
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.viewmodel.ChangePasswordViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.ProfileViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.SplashScreenViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.SubscriptionDetailsViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels.ForgetPasswordChangeViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels.ForgetPasswordRequestViewModel
import com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels.ForgetPasswordTryCodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

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

            SubscriptionBusApplicationTheme(
                darkTheme = false
            ) {
                NavHost(
                    navController = navController,
                    startDestination = SplashScreenRoute,
                    modifier = Modifier
                        .background(
                            appSurfaceColor
                        )
                        .windowInsetsPadding(WindowInsets.statusBars),
                ) {

                    composable<SplashScreenRoute>(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        }
                    ) {
                        SplashScreenApp(
                            navController = navController,
                            splashScreenViewModel = hiltViewModel<SplashScreenViewModel>()
                        )
                    }
                    composable<Login>(
                        enterTransition = { fadeIn(animationSpec = tween(easing = LinearEasing)) },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        }
                    ) {
                        LoginScreen(
                            navController = navController,
                            deviceName = appId,
                            appId = deviceId
                        )
                    }
                    composable<SignUp>(

                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        },
                        exitTransition = {
                            fadeOut(tween(easing = LinearEasing))
                        }
                    ) {
                        SignUpScreen(
                            navController = navController,
                            deviceId = appId,
                            appId = deviceId
                        )
                    }

                    composable<EmailConfirmation>(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        },
                        exitTransition = {
                            fadeOut(tween(easing = LinearEasing))
                        }
                    ) {
                        EmailConfirmationScreen(
                            navController = navController
                        )
                    }

                    composable<Dashboard>(
                        enterTransition = { fadeIn(animationSpec = tween(easing = LinearEasing)) },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        }
                    ) { backStackEntry ->
                        val idSubscription =
                            backStackEntry.savedStateHandle.get<Int>("id-subscription")
                        backStackEntry.savedStateHandle.remove<Int>("id-subscription")
                        val walletValue = backStackEntry.savedStateHandle.get<Double>("wallet")
                        backStackEntry.savedStateHandle.remove<Double>("wallet")
                        val sub =
                            if (idSubscription != null && walletValue != null) SubscribeResult(
                                idSubscription,
                                walletValue
                            ) else null

                        val messageBack = backStackEntry.toRoute<Dashboard>().messageBack
                        DashboardScreen(
                            navController = navController,
                            subscriptionReturnModel = sub,
                            messageBack = messageBack
                        )
                    }

                    composable<SubscriptionDetails>(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    easing = LinearEasing,
                                    delayMillis = 200
                                )
                            )
                        },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing, delayMillis = 200)
                            )
                        }
                    ) {
                        val argument = it.toRoute<SubscriptionDetails>()
                        SubscriptionDetailsScreen(
                            viewModel = hiltViewModel<SubscriptionDetailsViewModel, SubscriptionDetailsViewModel.Factory> { factory ->
                                factory.create(
                                    argument.subscriptionDetailsId,
                                    currentUserCanSubscribe = argument.canCurrentClientSubscribe
                                )
                            },
                            navController
                        )

                    }

                    composable<ClientStateNav>(
                        enterTransition = { fadeIn(animationSpec = tween(easing = LinearEasing)) },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        }
                    ) {
                        val argument = it.toRoute<ClientStateNav>()
                        ProfileScreen(
                            profileViewModel = hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory ->
                                factory.create(
                                    argument.toClientState()
                                )
                            }, navController
                        )

                    }

                    composable<ChangePassword>(
                        enterTransition = { fadeIn(animationSpec = tween(easing = LinearEasing)) },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        }
                    ) {
                        ChangePasswordScreen(
                            changePasswordViewModel = hiltViewModel<ChangePasswordViewModel>(),
                            navController = navController
                        )

                    }

                    composable<ForgetPasswordRequest>(
                        enterTransition = { fadeIn(animationSpec = tween(easing = LinearEasing)) },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(easing = LinearEasing)
                            )
                        }
                    ) {
                        ForgetPasswordRequestScreen(
                            navController = navController,
                            viewModel = hiltViewModel<ForgetPasswordRequestViewModel>()
                        )
                    }

                    composable<ForgetPasswordTryCode>(
                    ) {
                        val email = it.toRoute<ForgetPasswordTryCode>().email
                        ForgetPasswordTryCodeScreen(
                            tryCodeViewModel = hiltViewModel<ForgetPasswordTryCodeViewModel>(),
                            email = email,
                            navController = navController
                        )
                    }
                    composable<ForgetPasswordChangePassword> {
                        val email = it.toRoute<ForgetPasswordChangePassword>().email
                        val passport = it.toRoute<ForgetPasswordChangePassword>().passport


                        ForgetPasswordChangeScreen(
                            viewModel = hiltViewModel<ForgetPasswordChangeViewModel>(),
                            navController = navController,
                            email = email,
                            passport = passport
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
object EmailConfirmation


@Serializable
class SubscriptionDetails(
    val subscriptionDetailsId: Int,
    val canCurrentClientSubscribe: Boolean
)

@Serializable
object SplashScreenRoute

// for forget password flow
// first
@Serializable
object ForgetPasswordRequest

// second
@Serializable
class ForgetPasswordTryCode(
    val email: String
)

// third
@Serializable
class ForgetPasswordChangePassword(
    val email: String,
    val passport: String
)


@Serializable
data class SignUpSecondStep(
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val middleName: String? = null,
)

@Serializable
data class ClientStateNav(
    val firstName: String,
    val lastName: String,
    val email: String,
    val imagePath: String,
    val wallet: String,
    val deviceName: String,
    val appId: String,
    val phoneNumber: String
) {
    companion object {

    }
}


fun ClientStateNav.Companion.fromClientState(clientState: ClientState): ClientStateNav {
    return ClientStateNav(
        firstName = clientState.firstName,
        lastName = clientState.lastName,
        email = clientState.email,
        imagePath = clientState.imagePath,
        wallet = clientState.wallet.toString(),
        deviceName = clientState.deviceName,
        appId = clientState.appId,
        phoneNumber = clientState.phoneNumber

    )
}


fun ClientStateNav.toClientState(): ClientState {
    return ClientState(
        firstName = firstName,
        lastName = lastName,
        email = email,
        imagePath = imagePath,
        wallet = wallet.toDouble(),
        deviceName = deviceName,
        appId = appId,
        phoneNumber = phoneNumber

    )
}

@Serializable
object SignUp


@Serializable
object ChangePassword

@Serializable
data class Dashboard(
    val messageBack: String? = null
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



