package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.SubscriptionAllDetails

data class LoadCurrentSubscriptionState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val data: SubscriptionAllDetails? = null,
    val error: String? = null
)
