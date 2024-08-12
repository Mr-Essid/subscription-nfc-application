package com.example.subscriptionbusapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeResult(
    val subscriptionXId: Int,
    val currentWallet: Double
)
