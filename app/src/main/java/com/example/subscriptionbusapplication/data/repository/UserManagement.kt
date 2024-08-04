package com.example.subscriptionbusapplication.data.repository

import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import com.example.subscriptionbusapplication.data.models.Subscription
import com.example.subscriptionbusapplication.data.models.User
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part

interface UserManagement {
    fun imageResolve(requestBody: MultipartBody.Part): Flow<AppResponse<ImageResolverModel?>>

     fun register(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        phoneNumber: String,
        appId: String,
        deviceId: String,
        multipartBody: MultipartBody.Part
    ): Flow<AppResponse<User?>>

}