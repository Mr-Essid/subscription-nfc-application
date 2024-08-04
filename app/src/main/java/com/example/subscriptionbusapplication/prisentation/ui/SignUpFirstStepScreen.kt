package com.example.subscriptionbusapplication.prisentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.static_component.AppOutlinedEditText
import com.example.subscriptionbusapplication.prisentation.static_component.PrimaryButton
import com.example.subscriptionbusapplication.prisentation.static_component.SecondaryButton
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.title
import com.example.subscriptionbusapplication.prisentation.viewmodel.FirstStepSignUpViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream


@Composable
fun SignUpFirstStepScreen(
    navController: NavController,
    viewModel: FirstStepSignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    dataFlowRapper: DataFlowRapper
) {

    val context = LocalContext.current
    val errorMap = viewModel.mapError.observeAsState()
    if (errorMap.value != null) {
        println("error returned back")
    }

    val requestFocusForm = LocalFocusManager.current
    Scaffold { padding ->

        Box(
            Modifier
                .background(appSurfaceColor)
                .padding(padding)
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column {

                Box(
                    modifier = Modifier
                        .layoutId("logo-golder")
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "personal information", style = h3)
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedEditText(
                    value = viewModel.name,
                    onChange = {
                        viewModel.name = it
                    },
                    placeholder = "firstname",
                    errorMessage = errorMap.value?.get("firstname"),

                    )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedEditText(
                    value = viewModel.optionalMiddleName,
                    onChange = {
                        viewModel.optionalMiddleName = it
                    },
                    placeholder = "middle name (optional)",
                    errorMessage = errorMap.value?.get("middleName"),
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedEditText(
                    value = viewModel.lastname,
                    onChange = {
                        viewModel.lastname = it
                    },
                    placeholder = "lastname",
                    errorMessage = errorMap.value?.get("lastname"),
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedEditText(
                    value = viewModel.phoneNumber,
                    onChange = {
                        viewModel.phoneNumber = it
                    },
                    placeholder = "phone number",
                    errorMessage = errorMap.value?.get("phoneNumber"),
                )

                Spacer(modifier = Modifier.height(8.dp))
                PrimaryButton(
                    text = if (dataFlowRapper.isDataCollected()) "submit" else "next",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    requestFocusForm.clearFocus()

                    if (viewModel.validateData()) {
                        if (dataFlowRapper.isDataCollected()) {
                            viewModel.register(
                                dataFlowRapper,
                                MultipartBody.Part.createFormData(
                                    "image",
                                    filename = "image.png",

                                    // TODO: this is not recommended !!, !!
                                    context.contentResolver.openInputStream(dataFlowRapper.imageUri!!)!!.readBytes().toRequestBody(contentType = "image/png".toMediaType())
                                )
                            )
                        }
                        val data = viewModel.preprocessData()
                        navController.navigate(data)

                    }

                }
                Spacer(modifier = Modifier.height(0.dp))
                SecondaryButton(text = "cancel", modifier = Modifier.fillMaxWidth()) {
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 1.dp, color = appPrimaryColor)
                Spacer(modifier = Modifier.heightIn(8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "already have account", style = bodyText.copy(fontSize = 12.sp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "login account",
                        style = title.copy(fontSize = 12.sp),
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                }

            }

        }
    }
}

@Preview
@Composable
private fun SignUpFirstScreenPrev() {

}