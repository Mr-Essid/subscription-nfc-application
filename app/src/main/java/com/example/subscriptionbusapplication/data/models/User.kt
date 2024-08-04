package com.example.subscriptionbusapplication.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("app_id")
    val appId: String,
    @SerializedName("device_name")
    val deviceName: String,
    val email: String,
    val firstname: String,
    val password: String? = null,
    val lastname: String,
    @SerializedName("mail_verified")
    val mailVerified: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    val role: String? = null,
    val subscriptions: List<Subscription>? = null,
    val wallet: Double? = null
)