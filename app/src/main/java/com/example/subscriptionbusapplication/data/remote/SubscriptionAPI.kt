package com.example.subscriptionbusapplication.data.remote

import com.example.subscriptionbusapplication.data.models.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SubscriptionAPI {


    @Multipart
    @POST("/TODO")
    suspend fun register(
        @Part("firstname") firstname: String,
        @Part("lastname") lastname: String,
        @Part("email") email: String,
        @Part("password") password: String,
        @Part("phoneNumber") phoneNumber: String,
        @Part("appId") appId: String,
        @Part("deviceId") deviceId: String,
        multipartBody: MultipartBody.Part
    ): Response<User?>

}