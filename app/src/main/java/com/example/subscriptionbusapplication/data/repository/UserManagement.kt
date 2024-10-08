package com.example.subscriptionbusapplication.data.repository

import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.AccessTokenModel
import com.example.subscriptionbusapplication.data.models.ClientModel
import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import com.example.subscriptionbusapplication.data.models.Passport
import com.example.subscriptionbusapplication.data.models.Status
import com.example.subscriptionbusapplication.data.models.SubscribeResult
import com.example.subscriptionbusapplication.data.models.SubscriptionAllDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionX
import com.example.subscriptionbusapplication.data.models.User
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserManagement {
    fun imageResolve(requestBody: MultipartBody.Part): Flow<AppResponse<ImageResolverModel?>>

    fun register(
        firstname: MultipartBody.Part,
        lastname: MultipartBody.Part,
        email: MultipartBody.Part,
        password: MultipartBody.Part,
        phoneNumber: MultipartBody.Part,
        appId: MultipartBody.Part,
        deviceId: MultipartBody.Part,
        multipartBody: MultipartBody.Part
    ): Flow<AppResponse<User?>>


    fun login(
        email: String,
        password: String,
        deviceName: String,
        appId: String
    ): Flow<AppResponse<AccessTokenModel?>>

    fun loadCurrentClient(
        token: String
    ): Flow<AppResponse<ClientModel?>>


    fun loadSubscriptions(
        token: String
    ): Flow<AppResponse<List<SubscriptionDetails>?>>

    fun loadSubscription(
        token: String,
        subscriptionId: Int
    ): Flow<AppResponse<SubscriptionAllDetails?>>


    fun subscribe(
        token: String,
        subscriptionId: Int
    ): Flow<AppResponse<SubscribeResult?>>


    fun getSubscriptionXDetails(
        token: String,
        subscriptionXId: Int
    ): Flow<AppResponse<SubscriptionX?>>

    fun disconnect(
        token: String,
    ): Flow<AppResponse<Status?>>

    fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): Flow<AppResponse<Status?>>

    fun sendForgetPasswordRequest(email: String): Flow<AppResponse<Status?>>
    fun sendForgetPasswordRequestCode(email: String, code: String): Flow<AppResponse<Passport?>>
    fun sendForgetPasswordChange(
        email: String,
        passport: String,
        newPassword: String
    ): Flow<AppResponse<Status?>>

    fun checkTokenValidation(
        token: String
    ): Flow<AppResponse<Status?>>


}