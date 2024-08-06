package com.example.subscriptionbusapplication.prisentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
import com.example.subscriptionbusapplication.prisentation.viewmodel.FormNamesFirstStep
import com.example.subscriptionbusapplication.prisentation.viewmodel.FormNamesSecondStep
import com.example.subscriptionbusapplication.prisentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    deviceId: String,
    appId: String,
    navController: NavController

) {

    // android things
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    // state that should be on the whole page (those of type State<T> the current compose function will subscribe to the change of them, but cannot change any one of them)
    val errorMap = signUpViewModel.mapError
    val imageResolverState = signUpViewModel.imageResolveStateWatcher
    val registerResourcesState = signUpViewModel.registerResourcesStateWatcher
    val isImageAccepted = signUpViewModel.isImageAccepted


    // all statue specifically for UI
    val alpha = remember {
        Animatable(0f)
    }

    val beta = remember {
        Animatable(0f)
    }

    var step2color by remember {
        mutableStateOf(Color.Black)
    }

    var step3color by remember {
        mutableStateOf(Color.Black)
    }


    var imageColorFilter by remember {
        mutableStateOf<Color?>(null)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }


    // image Picker Contract
    val registryActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { imageUri ->
        imageUri?.let {
            signUpViewModel.clearImageAcceptance()
            signUpViewModel.arrayByteOfImage = imageUri
            // the function readBytes automatically close the stream
            val bytes_ = context.contentResolver.openInputStream(imageUri)?.readBytes()


            bytes_?.let {
                imageColorFilter = null
                signUpViewModel.clearImageResolveState()
                signUpViewModel.resolveImage(it)
            } ?: run {
                coroutineScope.launch {
                    when (snackBarHostState.showSnackbar("sorry, same things went wrong")) {
                        SnackbarResult.ActionPerformed -> {
                            println("action to be done: we will send email indicate that the function has been failed")
                        }

                        else -> {
                            println("the user needs same privacy")
                        }
                    }
                }
            }


        }
    }


    var currentPage by remember {
        mutableIntStateOf(1)
    }


    val animatedContentTransitionScope: AnimatedContentTransitionScope<Int>.() -> ContentTransform =
        {

            if (initialState < targetState)

                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start
                ) togetherWith slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start
                )
            else
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End
                ) togetherWith slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start
                )
        }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            if (
                registerResourcesState.value.isLoading
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { padding ->
        Box(
            modifier
                .background(appSurfaceColor)
                .padding(padding)
                .fillMaxSize()
        ) {


            Column(
                Modifier
                    .fillMaxSize()
                    .background(appSurfaceColor)
            ) {
                Spacer(modifier = Modifier.height(28.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            Modifier.padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                tint = appPrimaryColor,
                                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                                contentDescription = "first step",
                                modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        step3color = Color.Black
                                        beta.animateTo(0f)
                                        step2color = Color.Black
                                        alpha.animateTo(0f)
                                        signUpViewModel.currentPage = 1
                                    }
                                }
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .height(2.dp)
                                    .weight(1f)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colorStops = arrayOf(
                                                alpha.value to appPrimaryColor,
                                                alpha.value to Color.Transparent
                                            )

                                        )
                                    )
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                                contentDescription = "second step",
                                tint = step2color,
                                modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        if (signUpViewModel.validateFirstStep()) {
                                            step3color = Color.Black
                                            beta.animateTo(0f)
                                            signUpViewModel.currentPage = 2
                                            alpha.animateTo(1f)
                                            step2color = appPrimaryColor
                                        }

                                    }
                                }
                            )
                            HorizontalDivider(
                                modifier =
                                Modifier
                                    .height(2.dp)
                                    .weight(1f)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colorStops =
                                            arrayOf(
                                                beta.value to appPrimaryColor,
                                                beta.value to Color.Transparent
                                            )
                                        )
                                    )
                            )
                            Icon(
                                tint = step3color,
                                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                                contentDescription = "last step",
                                modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        if (signUpViewModel.validateFirstStep() && signUpViewModel.validateSecondStep()) {
                                            beta.animateTo(1f)
                                            signUpViewModel.currentPage = 3
                                            step3color = appPrimaryColor
                                        }

                                    }
                                }
                            )

                        }

                        Spacer(modifier = Modifier.height(18.dp))
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


                // where all content is applied
                AnimatedContent(
                    modifier = Modifier.weight(1f),
                    targetState = signUpViewModel.currentPage,
                    label = "swap content",
                    transitionSpec = animatedContentTransitionScope
                ) { targetSpace ->
                    // first step


                    if (targetSpace == 1)
                        Box(
                            Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxSize(),
                        ) {

                            Column {

                                Spacer(modifier = Modifier.height(32.dp))
                                Text(text = "personal information", style = h3)
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedEditText(
                                    value = signUpViewModel.firstname,
                                    onChange = {
                                        signUpViewModel.firstname = it
                                    },
                                    placeholder = "firstname",
                                    errorMessage = errorMap[FormNamesFirstStep.FIRSTNAME.name],

                                    )
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedEditText(
                                    value = signUpViewModel.middleName,
                                    onChange = {
                                        signUpViewModel.middleName = it
                                    },
                                    placeholder = "middle name (optional)",
                                    errorMessage = errorMap[FormNamesFirstStep.MIDDLE_NAME.name],
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedEditText(
                                    value = signUpViewModel.lastName,
                                    onChange = {
                                        signUpViewModel.lastName = it
                                    },
                                    placeholder = "lastname",
                                    errorMessage = errorMap[FormNamesFirstStep.LASTNAME.name],
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedEditText(
                                    value = signUpViewModel.phoneNumber,
                                    onChange = {
                                        signUpViewModel.phoneNumber = it
                                    },
                                    placeholder = "phone number",
                                    errorMessage = errorMap[FormNamesFirstStep.PHONE_NUMBER.name],
                                )

                                Spacer(modifier = Modifier.height(8.dp))


                                PrimaryButton(
                                    text = "next",
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (signUpViewModel.validateFirstStep()) {
                                        coroutineScope.launch {
                                            alpha.animateTo(1f)
                                            step2color = appPrimaryColor
                                            signUpViewModel.currentPage = 2
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.dp))
                                SecondaryButton(
                                    text = "cancel",
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                }
                            }
                        }

                    if (targetSpace == 2) {

                        Box(
                            Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxSize(),
                        ) {

                            Column {

                                Spacer(modifier = Modifier.height(32.dp))
                                Text(text = "account information", style = h3)
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedEditText(
                                    value = signUpViewModel.email,
                                    onChange = {
                                        signUpViewModel.email = it
                                    },
                                    placeholder = "email",
                                    errorMessage = errorMap[FormNamesSecondStep.EMAIL.name]
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedPassword(
                                    value = signUpViewModel.password,
                                    onChange = {
                                        signUpViewModel.password = it
                                    },
                                    placeholder = "password",
                                    errorMessage = errorMap[FormNamesSecondStep.PASSWORD.name]
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                AppOutlinedPassword(
                                    value = signUpViewModel.confirmedPassword,
                                    onChange = {
                                        signUpViewModel.confirmedPassword = it
                                    },
                                    placeholder = "confirm password",
                                    errorMessage = errorMap[FormNamesSecondStep.PASSWORD_CONFIRMATION.name]
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                PrimaryButton(
                                    text = "sign up",
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (signUpViewModel.validateSecondStep()) {
                                        coroutineScope.launch {
                                            beta.animateTo(1f)
                                            step3color = appPrimaryColor
                                            signUpViewModel.currentPage = 3
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.dp))
                                SecondaryButton(
                                    text = "cancel",
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                }
                                Spacer(modifier = Modifier.height(12.dp))


                            }

                        }
                    }


                    if (targetSpace == 3)
                        Column()
                        {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Column()
                                {
                                    Spacer(modifier = Modifier.height(28.dp))
                                    Text(text = "pick image with your face", style = h3)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    if (signUpViewModel.arrayByteOfImage == null) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_launcher_background),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .clickable {
                                                    imageColorFilter = null
                                                    registryActivityResult.launch(
                                                        PickVisualMediaRequest(
                                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                }
                                                .size(250.dp)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(
                                                    250.dp
                                                ), contentAlignment = Alignment.Center

                                        ) {

                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Transparent)
                                            ) {

                                                AsyncImage(
                                                    model = signUpViewModel.arrayByteOfImage,
                                                    contentDescription = "Selected Image",
                                                    modifier = Modifier

                                                        .width(250.dp)
                                                        .clip(
                                                            RoundedCornerShape(28.dp)
                                                        )
                                                        .clickable {
                                                            registryActivityResult.launch(
                                                                PickVisualMediaRequest(
                                                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                                                )
                                                            )
                                                        },
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = imageColorFilter?.let {
                                                        ColorFilter.tint(
                                                            it,
                                                            blendMode = BlendMode.Lighten
                                                        )
                                                    }
                                                )
                                                if (imageResolverState.value.isLoading)
                                                    CircularProgressIndicator()
                                                else if (imageResolverState.value.errorMessage != null) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.baseline_close_24),
                                                        contentDescription = "image refused",
                                                        tint = Color.Red,
                                                        modifier = Modifier.size(48.dp)
                                                    )

                                                    imageColorFilter = Color.Red.copy(alpha = 0.6f)
                                                } else if (
                                                    imageResolverState.value.data != null
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                                                        contentDescription = "image accepted",
                                                        tint = appPrimaryColor,
                                                        modifier = Modifier.size(48.dp)
                                                    )
                                                    imageColorFilter =
                                                        Color.Green.copy(alpha = 0.6f)
                                                }
                                            }
                                        }
                                    }


                                    Spacer(modifier = Modifier.height(28.dp))
                                    Column(modifier = Modifier.width(250.dp)) {
                                        PrimaryButton(
                                            text = "send", modifier = Modifier.fillMaxWidth(),
                                            enabled = isImageAccepted.value
                                        ) {
                                            // starting the process of registration
                                            assert(signUpViewModel.arrayByteOfImage != null)
                                            signUpViewModel.arrayByteOfImage?.let {


                                                // first get bytes
                                                // send bytes to registration viewModel


                                                // !! is not one of the best practice i know fault
                                                signUpViewModel.register(
                                                    context.contentResolver.openInputStream(it)!!
                                                        .readBytes(),
                                                    deviceId = deviceId,
                                                    appId = appId
                                                )


                                            } ?: run {
                                                println("unexpected error occur")
                                            }


                                        }
                                        SecondaryButton(
                                            text = "cancel",
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                        }
                                    }
                                }


                            }


                        }
                }


                // footer
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(thickness = 1.dp, color = appPrimaryColor)
                        Spacer(modifier = Modifier.heightIn(8.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "already have account",
                                style = bodyText.copy(fontSize = 12.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "login account",
                                style = title.copy(fontSize = 12.sp),
                                modifier = Modifier.clickable {
                                })
                        }

                        Spacer(modifier = Modifier.height(48.dp))
                    }

                }

            }
        }
    }

}

@Preview
@Composable
private fun SignUpPrev() {
}


