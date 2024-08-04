package com.example.subscriptionbusapplication.di

import com.example.subscriptionbusapplication.data.ImageResolverRetrofitInstance
import com.example.subscriptionbusapplication.data.SubscriptionServiceRetrofitInstance
import com.example.subscriptionbusapplication.data.remote.ImageResolveAPI
import com.example.subscriptionbusapplication.data.remote.SubscriptionAPI
import com.example.subscriptionbusapplication.domain.shared_usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SimpleModule {

    @Provides
    fun providesSubscriptionAPI(): SubscriptionAPI {
        return SubscriptionServiceRetrofitInstance.getInstance()
    }

    @Provides
    fun providesImageResolverAPI(): ImageResolveAPI {
        return ImageResolverRetrofitInstance.getInstance()
    }



    
}