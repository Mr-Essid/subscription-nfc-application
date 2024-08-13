package com.example.subscriptionbusapplication.data.models

data class SubscriptionAllDetails(
    val days: List<Day>,
    val id: Int,
    val label: String,
    val labelFrench: String,
    val lines: List<Line>,
    val months: Int,
    val price: Double,
    val zoneName: String
)