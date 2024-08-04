package com.example.subscriptionbusapplication.data.models

import com.google.gson.annotations.SerializedName

data class Subscription(
    val from: String,
    val id: Int,
    @SerializedName("subscription_details_id")
    val subscriptionDetailsId: Int,
    val to: String
)