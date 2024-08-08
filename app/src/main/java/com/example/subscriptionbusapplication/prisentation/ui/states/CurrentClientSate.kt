package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.ClientModel

data class CurrentClientSate(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val data: ClientModel? = null,
    val errorMessage: String? = null
)
