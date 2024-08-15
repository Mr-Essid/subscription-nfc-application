package com.example.subscriptionbusapplication.data.remote

import com.example.subscriptionbusapplication.data.models.AccessTokenModel
import com.example.subscriptionbusapplication.data.models.ClientModel
import com.example.subscriptionbusapplication.data.models.Passport
import com.example.subscriptionbusapplication.data.models.Status
import com.example.subscriptionbusapplication.data.models.SubscribeResult
import com.example.subscriptionbusapplication.data.models.SubscriptionAllDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionX
import com.example.subscriptionbusapplication.data.models.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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


    @Headers("Accept: application/json")
    @GET("/api/current-client")
    suspend fun loadCurrentClient(@Header("Authorization") token: String): Response<ClientModel>

    @Headers("Accept: application/json")
    @GET("/api/subscriptions")
    suspend fun loadSubscriptions(@Header("Authorization") token: String): Response<List<SubscriptionDetails>>

    @Headers("Accept: application/json")
    @GET("/api/subscriptions/{subscription_id}")
    suspend fun loadSubscriptionById(
        @Header("Authorization") token: String,
        @Path("subscription_id") subscriptionId: Int
    ): Response<SubscriptionAllDetails>


    @Headers("Accept: application/json")
    @POST("/api/subscriptions/subscribtion/{subscription_id}")
    suspend fun subscribe(
        @Header("Authorization") token: String,
        @Path("subscription_id") subscriptionId: Int
    ): Response<SubscribeResult?>


    @Headers("Accept: application/json")
    @GET("/api/subscriptions/current-client_/subscriptionx/{subscriptionx_id}")
    suspend fun subscriptionXById(
        @Header("Authorization") token: String,
        @Path("subscriptionx_id") subscriptionId: Int
    ): Response<SubscriptionX?>

    @Headers("Accept: application/json")
    @GET("/api/current-client/disconnect")
    suspend fun disconnect(
        @Header("Authorization") token: String,
    ): Response<Status>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PATCH("/api/current-client/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Field("newPassword") newPassword: String,
        @Field("oldPassword") oldPassword: String
    ): Response<Status>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/guest-client/forget-password")
    suspend fun forgetPasswordRequest(
        @Field("email") email: String
    ): Response<Status>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/guest-client/trycode-password")
    suspend fun forgetPasswordTryCode(
        @Field("code") code: String,
        @Field("email") email: String
    ): Response<Passport?>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/guest-client/change-password")
    suspend fun forgetPasswordChange(
        @Field("passport") passport: String,
        @Field("email") email: String,
        @Field("newPassword") password: String
    ): Response<Status?>

}