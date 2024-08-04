package com.example.subscriptionbusapplication.data.models

data class RegisterModel(
    val firstname: String,
    val lastname: String,
    val email: String,
    val appId: String,
    val deviceName: String,
    val password: String
)

