package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.CurrentClientSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionManagement: SessionManagement,
    private val userManagement: UserManagement
) : ViewModel() {

    private val _currentClientSate = mutableStateOf(CurrentClientSate())
    val currentClientSate: State<CurrentClientSate> = _currentClientSate

    init {
        loadCurrentUser()
    }


    private fun loadCurrentUser() {
        val token = sessionManagement.getToken()
        if (token.isEmpty()) {
            _currentClientSate.value = CurrentClientSate()
            return
        }
        userManagement.loadCurrentClient(token).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _currentClientSate.value = CurrentClientSate(isLoading = true)
                }

                is AppResponse.Success -> {
                    _currentClientSate.value =
                        CurrentClientSate(data = appResponse.data!!, isSuccess = true)
                }

                is AppResponse.Error -> {
                    _currentClientSate.value =
                        CurrentClientSate(isError = true, errorMessage = appResponse.message)
                }
            }
        }.launchIn(viewModelScope)

    }


    fun eraseState() {
        _currentClientSate.value = CurrentClientSate()
    }


}