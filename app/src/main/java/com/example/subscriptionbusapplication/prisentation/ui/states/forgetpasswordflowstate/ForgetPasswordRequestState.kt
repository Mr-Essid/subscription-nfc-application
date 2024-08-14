package com.example.subscriptionbusapplication.prisentation.ui.states.forgetpasswordflowstate

import com.example.subscriptionbusapplication.data.models.Status

data class ForgetPasswordRequestState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: Status? = null
)
