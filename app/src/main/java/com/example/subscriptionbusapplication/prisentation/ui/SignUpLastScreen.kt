package com.example.subscriptionbusapplication.prisentation.ui

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.data.ImageResolverRetrofitInstance
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appOnSuccessColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSuccessColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText
import com.example.subscriptionbusapplication.prisentation.ui.theme.errorColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.onErrorColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.onSecondary
import com.example.subscriptionbusapplication.prisentation.ui.theme.secondary
import com.example.subscriptionbusapplication.prisentation.ui.theme.title
import com.example.subscriptionbusapplication.prisentation.viewmodel.SignUpLastStepViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


@Composable
fun SignUpLastScreen(
    modifier: Modifier = Modifier,
    dataFlowRapper: DataFlowRapper? = null,
    lastStepViewModel: SignUpLastStepViewModel
) {

    val context = LocalContext.current
    // state observed from viewModel
    val imageResolveState = lastStepViewModel.imageResolveState

    // uri Observer
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    var buttonSubmitEnabled by remember {
        mutableStateOf(false)
    }


    var imageColorFilter by remember {
        mutableStateOf<Color?>(null)
    }
    // image Picker Contract
    val registryActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { imageUri ->

        imageUri?.let {


            lastStepViewModel.resetState()
            uri = imageUri


            lastStepViewModel.resolveImage(
                MultipartBody.Part.createFormData(
                    "file_",
                    "file.png",
                    context.contentResolver.openInputStream(uri!!)?.readBytes()!!
                        .toRequestBody(contentType = "image/png".toMediaType())
                )
            )


        }
    }



    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Spacer(modifier = Modifier.height(28.dp))
                        Text(text = "pick image with your face", style = h3)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (uri == null) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "image",
                                modifier = Modifier
                                    .clickable {
                                        registryActivityResult.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                    .size(250.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(
                                        250.dp
                                    )
                                    .clickable {
                                        lastStepViewModel.resetState()
                                        buttonSubmitEnabled = false
                                        imageColorFilter = null
                                        registryActivityResult.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }, contentAlignment = Alignment.Center

                            ) {

                                uri.let {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "content type",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(250.dp)
                                            .clip(RoundedCornerShape(24.dp)),

                                        colorFilter = imageColorFilter?.let { it1 ->
                                            ColorFilter.tint(
                                                it1.copy(alpha = 0.9f),
                                                blendMode = BlendMode.Lighten
                                            )
                                        }
                                    )
                                    imageResolveState.value.errorMessage?.let { message ->
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_close_24),
                                                contentDescription = "refused image",
                                                tint = onErrorColor
                                            )
                                            imageColorFilter = errorColor
                                            Text(text = message, style = h3, color = onErrorColor)

                                        }
                                    }
                                    if (imageResolveState.value.isLoading)
                                        CircularProgressIndicator()
                                    else if (imageResolveState.value.data != null) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                                                contentDescription = "refused image",
                                                tint = appOnSuccessColor
                                            )
                                            imageColorFilter = appSuccessColor
                                            Text(
                                                text = "image clear, thanks",
                                                style = h3,
                                                color = appOnSuccessColor
                                            )

                                        }

                                        buttonSubmitEnabled = true
                                    }


                                }
                            }
                        }


                        Spacer(modifier = Modifier.height(28.dp))
                        Column(modifier = Modifier.width(250.dp)) {
                            PrimaryButton(
                                text = "send", modifier = Modifier.fillMaxWidth(),
                                enabled = buttonSubmitEnabled
                            ) {
                            }
                            SecondaryButton(text = "cancel", modifier = Modifier.fillMaxWidth()) {
                            }
                        }
                    }


                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "already have account", style = bodyText.copy(fontSize = 12.sp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "login account",
                        style = title.copy(fontSize = 12.sp),
                        modifier = Modifier.clickable {
                        })
                }

            }

        }
    }

}


@Preview
@Composable
private fun SignUpLastStepScreenPref() {

    SignUpLastScreen(
        lastStepViewModel = hiltViewModel<SignUpLastStepViewModel>()
    )
}