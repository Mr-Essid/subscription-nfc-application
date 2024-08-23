package com.example.subscriptionbusapplication.prisentation.ui.states

data class ResponseSate<T>(
    val isLoading: Boolean = false,
    val code: Int? = null,
    val errorMessage: String? = null,
    val data: T? = null
)
