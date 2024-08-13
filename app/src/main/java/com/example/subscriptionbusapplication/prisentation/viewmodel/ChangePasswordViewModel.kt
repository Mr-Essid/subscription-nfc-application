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
import com.example.subscriptionbusapplication.prisentation.ui.states.ChangePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userManagement: UserManagement,
    private val sessionManagement: SessionManagement
) : ViewModel() {

    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var newPasswordConfirmation by mutableStateOf("")


    private val _changePasswordState = mutableStateOf(ChangePasswordState())
    val changePasswordState: State<ChangePasswordState> = _changePasswordState
    private val _errorMap = mutableStateMapOf<String, String>()
    val errorMap: Map<String, String> = _errorMap

    fun changePassword() {
        _errorMap.remove("confirmationError")

        if (newPassword != newPasswordConfirmation) {
            _errorMap["confirmationError"] = "Confirmation Error, wrong confirmation password"
            return
        }
        userManagement.changePassword(sessionManagement.getToken(), oldPassword, newPassword)
            .onEach { appResponse ->
                when (appResponse) {
                    is AppResponse.Success -> {
                        _changePasswordState.value = ChangePasswordState(data = appResponse.data!!)
                    }

                    is AppResponse.Error -> {
                        _changePasswordState.value = ChangePasswordState(
                            errorMessage = appResponse.message ?: "unexpected error just done"
                        )
                    }

                    is AppResponse.Loading -> {

                        _changePasswordState.value = ChangePasswordState(isLoading = true)
                    }

                }

            }.launchIn(viewModelScope)

    }


    fun clearState() {
        _changePasswordState.value = ChangePasswordState()
    }


    fun clearInput() {
        oldPassword = ""
        newPassword = ""
        newPasswordConfirmation = ""
    }

}