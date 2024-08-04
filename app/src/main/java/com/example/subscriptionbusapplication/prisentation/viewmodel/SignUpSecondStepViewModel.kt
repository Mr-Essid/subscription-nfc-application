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
import com.example.subscriptionbusapplication.data.repositoryImp.UserManagementRepositoryImp
import com.example.subscriptionbusapplication.domain.shared_usecase.RegisterUseCase
import com.example.subscriptionbusapplication.prisentation.ui.DataFlowRapper
import com.example.subscriptionbusapplication.prisentation.ui.states.RegisterResourcesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class SignUpSecondStepViewModel @Inject constructor(
    val registerUseCase: RegisterUseCase
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmationPassword by mutableStateOf("")
    private val _mapError = MutableLiveData<Map<String, String>?>(null)
    val mapError: LiveData<Map<String, String>?> = _mapError


    private val _registerState = mutableStateOf(RegisterResourcesState())
    var registerResourcesState: State<RegisterResourcesState> = _registerState


    fun validateData(): Boolean {
        _mapError.postValue(null)
        val errorMap = mutableMapOf<String, String>();
        val emailRegex =
            Regex("""^[A-Za-z0-9](?:[A-Za-z0-9-_$}{^&'!~%)+(]*[A-Za-z0-9]+)*@[A-Za-z0-9][A-Za-z1-9-]*(?:\.[A-Za-z0-9-]+)*$""")

        if (!emailRegex.matches(email.trim())) {
            errorMap["email"] = "email not valid"
            _mapError.postValue(errorMap)
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

            errorMap["password"] =
                "password invalid, contains at least letter uppercase, lowercase, number, and special"
            _mapError.postValue(errorMap)
            return false

        }

        if (password != confirmationPassword) {
            errorMap["passwordConfirmation"] = "confirmation does not the same as password"
            _mapError.postValue(errorMap)
            return false
        }

        return true
    }


    fun register(
        dataFlowRapper: DataFlowRapper,
        multipartBody: MultipartBody.Part
    ) {
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