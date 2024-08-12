package com.example.subscriptionbusapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeReturnModel(
    val currentWallet: Double,
    val subscriptionXId: Int
)
