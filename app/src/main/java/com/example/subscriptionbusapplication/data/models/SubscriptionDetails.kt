package com.example.subscriptionbusapplication.data.models

data class SubscriptionDetails(
    val id: Int,
    val label: String,
    val labelFrench: String,
    val months: Int,
    val price: Double,
    val zoneName: String

)