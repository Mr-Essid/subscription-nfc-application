package com.example.subscriptionbusapplication.data

import com.example.subscriptionbusapplication.data.remote.ImageResolveAPI
import com.example.subscriptionbusapplication.data.remote.SubscriptionAPI
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ImageResolverRetrofitInstance {
    private const val BASE_URL = "https://essid101010.eu.pythonanywhere.com"

    fun getInstance(): ImageResolveAPI {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)

        return Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient().newBuilder().addInterceptor(interceptor)
                .build()
        )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ImageResolveAPI::class.java)
    }
}

object SubscriptionServiceRetrofitInstance {
    private const val BASE_URL = "https://192.168.1.24:8001"

    fun getInstance(): SubscriptionAPI {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)


        return Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SubscriptionAPI::class.java)
    }

}