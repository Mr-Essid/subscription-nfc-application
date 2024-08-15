package com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.Passport
import com.example.subscriptionbusapplication.data.models.Status
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.ResponseSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordTryCodeViewModel @Inject constructor(
    private val userManagement: UserManagement
) : ViewModel() {


    private val _tryCodeState = mutableStateOf(ResponseSate<Passport>())
    val tryCodeState: State<ResponseSate<Passport>> = _tryCodeState

    private val _resendEmailState = mutableStateOf(ResponseSate<Status>())
    val resendEmailState: State<ResponseSate<Status>> = _resendEmailState


    var codeValue by mutableStateOf("")


    fun resendEmail(email: String) {
        userManagement.sendForgetPasswordRequest(email).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Success -> {

                    _resendEmailState.value = ResponseSate(data = appResponse.data)
                }

                is AppResponse.Loading -> {
                    _resendEmailState.value = ResponseSate(isLoading = true)
                }

                is AppResponse.Error -> {
                    _resendEmailState.value = ResponseSate(
                        errorMessage = appResponse.message ?: "unexpected error just done"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun tryCode(email: String) {
        if (codeValue.length != 4) return


        userManagement.sendForgetPasswordRequestCode(email, codeValue).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Success -> {

                    _tryCodeState.value = ResponseSate(data = appResponse.data)
                }

                is AppResponse.Loading -> {
                    _tryCodeState.value = ResponseSate(isLoading = true)
                }

                is AppResponse.Error -> {
                    _tryCodeState.value = ResponseSate(
                        errorMessage = appResponse.message ?: "unexpected error just done"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearStateTryCode() {
        _tryCodeState.value = ResponseSate()
    }

    fun clearStateResend() {
        _resendEmailState.value = ResponseSate()
    }

}