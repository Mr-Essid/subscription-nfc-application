package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.models.CheckTokenResponse
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.ResponseSate
import com.example.subscriptionbusapplication.prisentation.ui.states.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val userManagement: UserManagement,
    val sessionManagement: SessionManagement
) : ViewModel() {


    private val _checkTokenState = mutableStateOf(ResponseSate<CheckTokenResponse?>())
    val checkTokenState: State<ResponseSate<CheckTokenResponse?>> = _checkTokenState
    private val _screenState = mutableStateOf(ScreenState.INIT_STATE)
    val screenState: State<ScreenState> = _screenState


    fun checkToken() {
        val token = sessionManagement.getToken()
        println("request process begin")
        if (token.isBlank()) {
            submitError("token not reachable", 0)
            println("there is not token")
            return
        }
        userManagement.checkTokenValidation(token).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _screenState.value = ScreenState.LOADING_STATE
                }

                is AppResponse.Success -> {
                    _screenState.value = ScreenState.NORMAL_STATE
                    _checkTokenState.value = ResponseSate(
                        data = CheckTokenResponse(
                            1,
                            message = appResponse.data?.status ?: "token valid"
                        ), code = 200
                    )
                }

                is AppResponse.Error -> {
                    submitError(
                        appResponse.message ?: "unexpected error occurs",
                        code = appResponse.code ?: 1
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

    fun eraseState() {
        _checkTokenState.value = ResponseSate()
    }


    private fun submitError(message: String = "unexpected error occurs", code: Int) {
        _checkTokenState.value = ResponseSate(errorMessage = message, code = code)
        _screenState.value = ScreenState.ERROR_STATE
    }

    private fun submitData(data: CheckTokenResponse) {
        _checkTokenState.value = ResponseSate(data = data)
        _screenState.value = ScreenState.NORMAL_STATE
    }


}