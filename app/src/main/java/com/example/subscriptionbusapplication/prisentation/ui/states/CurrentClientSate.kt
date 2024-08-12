package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.ClientModel

data class CurrentClientLoadState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
