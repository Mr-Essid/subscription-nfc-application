package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.domain.shared_usecase.RegisterUseCase
import com.example.subscriptionbusapplication.prisentation.ui.DataFlowRapper
import com.example.subscriptionbusapplication.prisentation.ui.SignUpSecondStep
import com.example.subscriptionbusapplication.prisentation.ui.states.RegisterResourcesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class FirstStepSignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    var name by mutableStateOf("")
    var lastname by mutableStateOf("")
    var optionalMiddleName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    private val _mapError = MutableLiveData<Map<String, String>>(null)
    val mapError: LiveData<Map<String, String>> = _mapError


    private val _registerState = mutableStateOf(RegisterResourcesState())
    var registerResourcesState: State<RegisterResourcesState> = _registerState


    fun preprocessData(): SignUpSecondStep {

        return SignUpSecondStep(
            firstname = name.trim().lowercase(),
            lastname = lastname.trim().lowercase(),
            phoneNumber = phoneNumber.trim(),
            middleName = optionalMiddleName.ifBlank { null },
        )


    }

    fun validateData(): Boolean {

        val regex = Regex("[A-Za-z]{2,254}")
        val regexPhoneNumber =
            Regex("""(((\+\d{1,4})|(\(\d{1,4}\)))[ -])?\d{2,3}[ -]?\d{3}[ -]?\d{3,4}""")
        val errorMap = mutableMapOf<String, String>()
        val errorMessage = "%s 2, 255 length and contains only letters"

        if (!regex.matches(name)) errorMap["firstname"] = errorMessage.format("firstname")
        if (!regex.matches(lastname)) errorMap["lastname"] = errorMessage.format("lastname")
        if (optionalMiddleName.isNotEmpty() && !regex.matches(optionalMiddleName)) errorMap["middleName"] =
            errorMessage.format("middle name")
        if (!regexPhoneNumber.matches(phoneNumber)) errorMap["phoneNumber"] =
            "phone number not correct"


        if (errorMap.isNotEmpty()) {
            _mapError.postValue(errorMap)
            return false
        }

        return true


    }

    fun register(
        dataFlowRapper: DataFlowRapper,
        multipartBody: MultipartBody.Part
    ) {
        dataFlowRapper.firstname = name
        dataFlowRapper.lastname = lastname
        dataFlowRapper.middleName = optionalMiddleName
        dataFlowRapper.phoneNumber = phoneNumber
        registerUseCase(
            dataFlowRapper,
            multipartBody
        ).onEach { appResponse ->
            when(appResponse) {
                is AppResponse.Loading -> _registerState.value = RegisterResourcesState(isLoading = true)
                is AppResponse.Success -> _registerState.value = RegisterResourcesState(data = appResponse.data)
                is AppResponse.Error -> _registerState.value = RegisterResourcesState(errorMessage = appResponse.message)
            }

        }.launchIn(viewModelScope)
    }

}