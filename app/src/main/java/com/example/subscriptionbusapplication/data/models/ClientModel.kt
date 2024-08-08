package com.example.subscriptionbusapplication.data.models

data class ClientModel(
    val appId: String,
    val deviceName: String,
    val email: String,
    val emailVerified: Int,
    val firstName: String,
    val imagePath: String,
    val lastName: String,
    val phoneNumber: String,
    val subscriptions: List<SubscriptionX>,
    val wallet: Double
)