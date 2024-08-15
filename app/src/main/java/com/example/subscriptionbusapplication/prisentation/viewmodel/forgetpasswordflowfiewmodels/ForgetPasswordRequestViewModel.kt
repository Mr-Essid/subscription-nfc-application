package com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.Status
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.ResponseSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordRequestViewModel @Inject constructor(
    private val userManagement: UserManagement
) : ViewModel() {

    var emailValue by mutableStateOf("")
    private val _emailRequestState = mutableStateOf(ResponseSate<Status?>())
    val emailRequestState: State<ResponseSate<Status?>> = _emailRequestState
    private val _mapError = mutableStateMapOf<String, String>()
    val mapError: Map<String, String> = _mapError


    fun submitRequest() {
        _mapError.remove("email")

        val emailRegex =
            Regex("""^[A-Za-z0-9](?:[A-Za-z0-9-_$}{^&'!~%)+(]*[A-Za-z0-9]+)*@[A-Za-z0-9][A-Za-z1-9-]*(?:\.[A-Za-z0-9-]+)+$""")

        if (!emailRegex.matches(emailValue)) {
            _mapError["email"] = "incorrect email, please select valid email"
            return;
        }

        userManagement.sendForgetPasswordRequest(emailValue).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Success -> {
                    _emailRequestState.value = ResponseSate(data = appResponse.data)
                }

                is AppResponse.Loading -> {
                    _emailRequestState.value = ResponseSate(isLoading = true)
                }

                is AppResponse.Error -> {
                    _emailRequestState.value = ResponseSate(
                        errorMessage = appResponse.message ?: "unexpected error just done"
                    )
                }
            }
        }.launchIn(scope = viewModelScope)

    }


    fun clearState() {
        _emailRequestState.value = ResponseSate()
    }

}