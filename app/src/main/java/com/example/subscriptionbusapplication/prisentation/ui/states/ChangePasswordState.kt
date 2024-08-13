package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.Status

data class ChangePasswordState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: Status? = null
)
