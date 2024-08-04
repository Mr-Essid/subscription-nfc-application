package com.example.subscriptionbusapplication.data.remote

import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageResolveAPI {

    @Multipart
    @POST("/resolve_image")
    suspend fun resolveImage(@Part requestBody: MultipartBody.Part): Response<ImageResolverModel>
}