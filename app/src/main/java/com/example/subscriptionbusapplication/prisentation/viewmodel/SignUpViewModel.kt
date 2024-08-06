package com.example.subscriptionbusapplication.prisentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.domain.shared_usecase.RegisterUseCase
import com.example.subscriptionbusapplication.prisentation.ui.states.ImageResolveState
import com.example.subscriptionbusapplication.prisentation.ui.states.RegisterResourcesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


enum class FormNamesFirstStep {
    FIRSTNAME,
    LASTNAME,
    PHONE_NUMBER,
    MIDDLE_NAME,
}

enum class FormNamesSecondStep {
    EMAIL,
    PASSWORD,
    PASSWORD_CONFIRMATION
}


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val userManagement: UserManagement
) : ViewModel() {
    var firstname by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var middleName by mutableStateOf("")

    // second step compose
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmedPassword by mutableStateOf("")

    // last step compose array of bytes
    var arrayByteOfImage by mutableStateOf<Uri?>(null)

    // error map
    //private val _mapError: MutableLiveData<MutableMap<String, String>> = MutableLiveData(mapOf())
    // val mapError: LiveData<Map<String, String>> = _mapError

    private val _mapError = mutableStateMapOf<String, String>()
    val mapError: Map<String, String> = _mapError

    // register State
    private val _registerState = mutableStateOf(RegisterResourcesState())
    val registerResourcesStateWatcher: State<RegisterResourcesState> = _registerState

    // image ResolveState
    private val _imageResolveState = mutableStateOf(ImageResolveState())
    val imageResolveStateWatcher: State<ImageResolveState> = _imageResolveState


    // state the acceptance stats of image
    private val _isImageAccepted = mutableStateOf(false)
    val isImageAccepted: State<Boolean> = _isImageAccepted

    // current page
    var currentPage by mutableIntStateOf(1)


    fun validateFirstStep(
    ): Boolean {

        FormNamesFirstStep.entries.forEach {
            _mapError.remove(it.name)
        }

        val regex = Regex("[A-Za-z]{2,254}")
        val regexPhoneNumber =
            Regex("""(((\+\d{1,4})|(\(\d{1,4}\)))[ -])?\d{2,3}[ -]?\d{3}[ -]?\d{3,4}""")
        val errorMessage = "%s 2, 255 length and contains only letters"

        if (!regex.matches(firstname)) _mapError[FormNamesFirstStep.FIRSTNAME.name] =
            errorMessage.format("firstname")
        if (!regex.matches(lastName)) _mapError[FormNamesFirstStep.LASTNAME.name] =
            errorMessage.format("lastname")
        if (middleName.isNotEmpty() && !regex.matches(middleName)) _mapError[FormNamesFirstStep.MIDDLE_NAME.name] =
            errorMessage.format("middle name")
        if (!regexPhoneNumber.matches(phoneNumber)) _mapError[FormNamesFirstStep.PHONE_NUMBER.name] =
            "phone number not correct"

        if (mapError.keys.containsAny(FormNamesFirstStep.entries.map { it.name })) {
            return false
        }
        return true

    }

    fun validateSecondStep(): Boolean {

        FormNamesSecondStep.entries.forEach {
            _mapError.remove(it.name)
        }


        val emailRegex =
            Regex("""^[A-Za-z0-9](?:[A-Za-z0-9-_$}{^&'!~%)+(]*[A-Za-z0-9]+)*@[A-Za-z0-9][A-Za-z1-9-]*(?:\.[A-Za-z0-9-]+)+$""")

        if (!emailRegex.matches(email.trim())) {
            _mapError[FormNamesSecondStep.EMAIL.name] = "Email not valid, please check your email"
            return false
        }

        if (!(
                    password.contains(regex = "[A-Z]".toRegex())
                            && password.contains(regex = "[a-z]".toRegex())
                            && password.contains("[0-1]".toRegex())
                            && password.contains("""\W""".toRegex())
                            && password.length > 8
                    )
        ) {

            _mapError[FormNamesSecondStep.PASSWORD.name] =
                "password invalid, contains at least letter uppercase, lowercase, number, and special"
            return false

        }

        if (password != confirmedPassword) {
            _mapError[FormNamesSecondStep.PASSWORD_CONFIRMATION.name] =
                "confirmation does not the same as password"
            return false
        }

        return true
    }


    fun register(byteArray: ByteArray, deviceId: String, appId: String) {

        println("registration begin")
        userManagement.register(
            firstname = MultipartBody.Part.createFormData("firstname", firstname),
            lastname = MultipartBody.Part.createFormData("lastname", "$lastName $middleName"),
            email = MultipartBody.Part.createFormData("email", email),
            password = MultipartBody.Part.createFormData("password", password),
            phoneNumber = MultipartBody.Part.createFormData("phoneNumber", phoneNumber),
            appId = MultipartBody.Part.createFormData("appId", appId),
            deviceId = MultipartBody.Part.createFormData("deviceName", deviceId),
            multipartBody = MultipartBody.Part.createFormData(
                "image",
                "image.png",
                byteArray.toRequestBody(contentType = "image/png".toMediaType())
            )
        ).onEach { appResponse ->

            when (appResponse) {
                is AppResponse.Success -> {
                    println("this is it the data is came ${appResponse.data}")
                    _registerState.value = RegisterResourcesState(data = appResponse.data)
                }

                is AppResponse.Loading -> {
                    _registerState.value = RegisterResourcesState(isLoading = true)

                }

                else -> {
                    //  the error case

                    if (appResponse.code == 422) {
                        appResponse.errorModalModel422?.let {
                            it.errors.phoneNumber?.let { phoneNumberError ->
                                phoneNumber = ""
                                _mapError[FormNamesFirstStep.PHONE_NUMBER.name] =
                                    phoneNumberError[0]
                                currentPage = 1
                            }

                            it.errors.email?.let { emailErrors ->
                                email = ""
                                _mapError[FormNamesSecondStep.EMAIL.name] = emailErrors[0]
                                if (currentPage != 1)
                                    currentPage = 2
                            }
                        }


                        _registerState.value = RegisterResourcesState()
                    } else {
                        _registerState.value = RegisterResourcesState(
                            errorMessage = appResponse.message ?: "unexpected error just happened"
                        )
                    }


                }
            }
        }.launchIn(viewModelScope)


    }


    fun resolveImage(
        imageBytes: ByteArray
    ) {

        val imageBody = MultipartBody.Part.createFormData(
            "file_",
            "image.png",
            imageBytes.toRequestBody(contentType = "image/png".toMediaType())
        )
        userManagement.imageResolve(imageBody).onEach { statusRequested ->
            when (statusRequested) {
                is AppResponse.Error -> {
                    _imageResolveState.value =
                        ImageResolveState(data = null, errorMessage = "this is problem")
                    _isImageAccepted.value = false
                }

                is AppResponse.Success -> {
                    _imageResolveState.value =
                        ImageResolveState(data = statusRequested.data)
                    _isImageAccepted.value = true
                }

                is AppResponse.Loading -> {
                    _imageResolveState.value =
                        ImageResolveState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }


    fun clearImageAcceptance() {
        _isImageAccepted.value = false
    }

    fun clearImageResolveState() {
        _imageResolveState.value = ImageResolveState()
    }


    fun clearRegistrationError() {
        _registerState.value = RegisterResourcesState()
    }

}


fun <E> Set<E>.containsAny(collection: Collection<E>): Boolean {
    // note that lookup collection inside collection take O(N*M) but here we have a set it mean O(N)

    for (element in collection) {
        if (this.contains(element)) {
            return true
        }
    }
    return false
}