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
import com.example.subscriptionbusapplication.prisentation.viewmodel.FormNamesSecondStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ForgetPasswordChangeViewModel @Inject constructor(
    private val userManagement: UserManagement
) : ViewModel() {

    private val _changePasswordState = mutableStateOf(ResponseSate<Status?>())
    val changePasswordState: State<ResponseSate<Status?>> = _changePasswordState

    var newPassword by mutableStateOf("")
    var passwordConfirmation by mutableStateOf("")

    private val _errorMap = mutableStateMapOf<String, String>()
    val errorMap: Map<String, String> = _errorMap

    fun sendData(email: String, passport: String) {
        formData.entries.forEach {
            _errorMap.remove(it.name)
        }

        if (!(
                    newPassword.contains(regex = "[A-Z]".toRegex())
                            && newPassword.contains(regex = "[a-z]".toRegex())
                            && newPassword.contains("[0-1]".toRegex())
                            && newPassword.contains("""\W""".toRegex())
                            && newPassword.length > 8
                    )
        ) {

            _errorMap[formData.PASSWORD.name] =
                "new password invalid, contains at least letter uppercase, lowercase, number, and special"

            return
        }

        if (newPassword != passwordConfirmation) {
            _errorMap[formData.PASSWORD.name] =
                "incorrect password confirmation"
            return
        }


        userManagement.sendForgetPasswordChange(email, passport, newPassword)
            .onEach { appResponse ->
                when (appResponse) {
                    is AppResponse.Loading -> {
                        _changePasswordState.value = ResponseSate(isLoading = true)
                    }

                    is AppResponse.Success -> {
                        _changePasswordState.value = ResponseSate(data = appResponse.data)
                    }

                    is AppResponse.Error -> {
                        _changePasswordState.value =
                            ResponseSate(errorMessage = appResponse.message)
                    }
                }
            }.launchIn(viewModelScope)


    }

    companion object {
        enum class formData {
            PASSWORD,
            PASSWORD_CONFIRMATION
        }
    }


    fun clearState() {
        _changePasswordState.value = ResponseSate()
    }
}
