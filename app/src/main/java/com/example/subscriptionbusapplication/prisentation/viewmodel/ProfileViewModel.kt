package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.ClientState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    private val userManagement: UserManagement,
    private val sessionManagement: SessionManagement,
    @Assisted val clientState: ClientState
) : ViewModel() {

    private val _logoutState = mutableStateOf(false)
    val logoutState: State<Boolean> = _logoutState

    @AssistedFactory
    interface Factory {
        fun create(clientState: ClientState): ProfileViewModel
    }

    fun disconnect() {
        userManagement.disconnect(sessionManagement.getToken()).onEach { appResponse ->
            sessionManagement.clearToken()
            sessionManagement
            _logoutState.value = true
        }.launchIn(viewModelScope)
    }


    fun clearState() {
        _logoutState.value = false
    }


}