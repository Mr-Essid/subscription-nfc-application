package com.example.subscriptionbusapplication.data

import com.example.subscriptionbusapplication.Constants
import com.example.subscriptionbusapplication.data.remote.ImageResolveAPI
import com.example.subscriptionbusapplication.data.remote.SubscriptionAPI
import com.example.subscriptionbusapplication.helpers.LocalDateJsonAdapter
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit


object ImageResolverRetrofitInstance {
    private const val BASE_URL = "https://essid101010.eu.pythonanywhere.com"

    fun getInstance(): ImageResolveAPI {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient().newBuilder().addInterceptor(interceptor)

                .build()
        )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ImageResolveAPI::class.java)
    }
}

object SubscriptionServiceRetrofitInstance {
    private val BASE_URL = Constants.BASE_URL

    fun getInstance(): SubscriptionAPI {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val gson = Gson().newBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateJsonAdapter()).create()

        return Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient().newBuilder().addInterceptor(interceptor)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).build()
        )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(SubscriptionAPI::class.java)
    }

}