package com.example.subscriptionbusapplication.data.repositoryImp

import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.ErrorModel422
import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import com.example.subscriptionbusapplication.data.models.User
import com.example.subscriptionbusapplication.data.remote.ImageResolveAPI
import com.example.subscriptionbusapplication.data.remote.SubscriptionAPI
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.fromStatusCodeToMessage
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okio.IOException
import retrofit2.HttpException
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
                    emit(
                        AppResponse.Error(
                            message = fromStatusCodeToMessage(statusCode = data.code()),
                            code = data.code()
                        )
                    )
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

    override fun register(
        firstname: MultipartBody.Part,
        lastname: MultipartBody.Part,
        email: MultipartBody.Part,
        password: MultipartBody.Part,
        phoneNumber: MultipartBody.Part,
        appId: MultipartBody.Part,
        deviceId: MultipartBody.Part,
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
            if (data.code() == 422) {
                val errorResponseModel422 =
                    Gson().fromJson(data.errorBody()?.string(), ErrorModel422::class.java)
                emit(
                    AppResponse.Error(
                        message = data.message(),
                        code = 422,
                        errorModel422 = errorResponseModel422
                    )
                )
            } else if (data.code() != 200) {
                emit(AppResponse.Error(message = data.message(), code = data.code()))
            } else {
                emit(AppResponse.Success(data.body()!!))
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