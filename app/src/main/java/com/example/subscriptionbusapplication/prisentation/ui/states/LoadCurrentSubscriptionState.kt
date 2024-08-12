package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.SubscriptionAllDetailsDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails

data class LoadCurrentSubscriptionState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val data: SubscriptionAllDetailsDetails? = null,
    val error: String? = null
)
