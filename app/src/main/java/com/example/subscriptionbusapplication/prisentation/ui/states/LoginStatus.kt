package com.example.subscriptionbusapplication.prisentation.ui.states

data class LoginStatus(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val data: String? = null,
    val errorMessage: String? = null,
    val code: Int? = null
)