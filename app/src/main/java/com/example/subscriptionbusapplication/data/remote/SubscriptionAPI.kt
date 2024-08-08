package com.example.subscriptionbusapplication.data.remote

import com.example.subscriptionbusapplication.data.models.AccessTokenModel
import com.example.subscriptionbusapplication.data.models.ClientModel
import com.example.subscriptionbusapplication.data.models.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SubscriptionAPI {


    @Multipart
    @Headers("Accept: application/json")
    @POST("/api/v1/client/register")
    suspend fun register(
        @Part firstname: MultipartBody.Part,
        @Part lastname: MultipartBody.Part,
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part,
        @Part phoneNumber: MultipartBody.Part,
        @Part appId: MultipartBody.Part,
        @Part deviceId: MultipartBody.Part,
        @Part multipartBody: MultipartBody.Part
    ): Response<User?>

    @Multipart
    @Headers("Accept: application/json")
    @POST("/api/v1/client/access-token")
    suspend fun login(
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part,
        @Part deviceName: MultipartBody.Part,
        @Part appId: MultipartBody.Part
    ): Response<AccessTokenModel?>


    @GET("/api/current-client")
    suspend fun loadCurrentClient(@Header("Authorization") token: String): Response<ClientModel>
}