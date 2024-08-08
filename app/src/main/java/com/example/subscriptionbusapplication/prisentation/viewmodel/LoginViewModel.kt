package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.LoginStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManagement: UserManagement,
    private val sessionManagement: SessionManagement
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")


    private val _errorMap = mutableStateMapOf<String, String>()
    val errorMap: Map<String, String> = _errorMap

    private val _loginStatus = mutableStateOf(LoginStatus())
    val loginStatus: State<LoginStatus> = _loginStatus

    fun validateData(): Boolean {
        _errorMap.remove("email")
        _errorMap.remove("password")

        val emailRegex =
            Regex("""^[A-Za-z0-9](?:[A-Za-z0-9-_$}{^&'!~%)+(]*[A-Za-z0-9]+)*@[A-Za-z0-9][A-Za-z1-9-]*(?:\.[A-Za-z0-9-]+)*$""")

        if (!emailRegex.matches(email)) {
            _errorMap["email"] = "invalid email, please check your email"
            return false
        }
        return true
    }

    fun submitAndListen(deviceName: String, appId: String) {
        clearState()
        userManagement.login(
            email,
            password,
            deviceName = deviceName,
            appId = appId
        ).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _loginStatus.value = LoginStatus(isLoading = true)
                }

                is AppResponse.Error -> {
                    if (appResponse.code == 422) {
                        val model422 = appResponse.errorModalModel422
                        print("This is Model of 422 $model422")
                        model422?.let {
                            if (model422.errors.email != null) {
                                _errorMap["email"] = model422.errors.email[0]
                            }
                            if (model422.errors.password != null) {
                                _errorMap["password"] = model422.errors.password[0]
                            }
                        }
                    }

                    _loginStatus.value = LoginStatus(
                        isError = true,
                        errorMessage = appResponse.message ?: "unexpected error happened",
                        code = appResponse.code
                    )

                }

                else -> {
                    assert(appResponse.data != null)
                    sessionManagement.putToken(appResponse.data!!.accessToken)
                    _loginStatus.value = LoginStatus(
                        isSuccess = true,
                        data = appResponse.data.accessToken,
                        code = appResponse.code
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun clearState() {
        _loginStatus.value = LoginStatus()
    }


    fun clean() {
        // clean
        email = ""
        password = ""
    }

}