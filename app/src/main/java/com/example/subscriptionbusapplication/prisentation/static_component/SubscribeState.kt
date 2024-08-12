package com.example.subscriptionbusapplication.prisentation.static_component

import com.example.subscriptionbusapplication.data.models.SubscribeResult

data class SubscribeState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: SubscribeResult? = null

)
