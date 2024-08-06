package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.User

class RegisterResourcesState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val errorMessage: String? = null
)