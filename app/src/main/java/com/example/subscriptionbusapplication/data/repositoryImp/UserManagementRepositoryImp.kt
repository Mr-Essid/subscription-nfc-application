package com.example.subscriptionbusapplication.data.repositoryImp

import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import com.example.subscriptionbusapplication.data.models.Subscription
import com.example.subscriptionbusapplication.data.models.User
import com.example.subscriptionbusapplication.data.remote.ImageResolveAPI
import com.example.subscriptionbusapplication.data.remote.SubscriptionAPI
import com.example.subscriptionbusapplication.data.repository.UserManagement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class UserManagementRepositoryImp @Inject constructor(
    val subscriptionAPI: SubscriptionAPI,
    private val imageResolveAPI: ImageResolveAPI
) : UserManagement {


    override fun imageResolve(requestBody: MultipartBody.Part): Flow<AppResponse<ImageResolverModel?>> =
        flow {
            emit(AppResponse.Loading(isLoading = true, data = null))
            try {
                val data = imageResolveAPI.resolveImage(requestBody)
                if (data.code() != 200 && !data.isSuccessful) {
                    emit(AppResponse.Error(message = data.message(), code = data.code()))
                } else {
                    emit(AppResponse.Success(data.body()))
                }
            } catch (e: IOException) {
                // for all kind of network, socket error (timeout, connection error, write to closed socket
                emit(AppResponse.Error(message = e.localizedMessage, code = -1))
            } catch (e: HttpException) {
                // for all kind for server error
                emit(AppResponse.Error(message = e.localizedMessage, code = -2))
            }
        }

    override suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        phoneNumber: String,
        appId: String,
        deviceId: String,
        multipartBody: MultipartBody.Part
    ): Flow<AppResponse<User?>> = flow {
        emit(AppResponse.Loading(isLoading = true, data = null))
        try {
            val data = subscriptionAPI.register(
                firstname,
                lastname,
                email,
                password,
                phoneNumber,
                appId,
                deviceId,
                multipartBody
            )
            if (data.code() != 200 && !data.isSuccessful) {
                emit(AppResponse.Error(message = data.message(), code = data.code()))
            } else {
                emit(AppResponse.Success())
            }
        } catch (e: IOException) {
            // for all kind of network, socket error (timeout, connection error, write to closed socket
            emit(AppResponse.Error(message = e.localizedMessage, code = -1))
        } catch (e: HttpException) {
            // for all kind for server error
            emit(AppResponse.Error(message = e.localizedMessage, code = -2))
        }
    }


}