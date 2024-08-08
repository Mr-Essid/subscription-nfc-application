package com.example.subscriptionbusapplication.data.models

import java.time.LocalDate

data class SubscriptionX(
    val from: LocalDate,
    val id: Int,
    val subscriptionDetails: SubscriptionDetails,
    val to: LocalDate
)