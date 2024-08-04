package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.ImageResolverModel

data class ImageResolveState(
    val data: ImageResolverModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
