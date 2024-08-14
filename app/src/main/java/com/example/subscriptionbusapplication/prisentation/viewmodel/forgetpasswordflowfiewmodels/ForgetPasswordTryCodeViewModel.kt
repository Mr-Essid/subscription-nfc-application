package com.example.subscriptionbusapplication.prisentation.viewmodel.forgetpasswordflowfiewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.forgetpasswordflowstate.ForgetPasswordRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordTryCodeViewModel @Inject constructor(
    private val userManagement: UserManagement
) : ViewModel() {


    var codeValue = mutableStateOf("")


    fun submitRequest() {

    }

    fun clearState() {
    }

}