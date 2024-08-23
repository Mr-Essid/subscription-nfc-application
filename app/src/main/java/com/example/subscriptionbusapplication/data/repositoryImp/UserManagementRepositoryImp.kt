package com.example.subscriptionbusapplication.data.repositoryImp

import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.AccessTokenModel
import com.example.subscriptionbusapplication.data.models.ClientModel
import com.example.subscriptionbusapplication.data.models.ErrorModel422
import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import com.example.subscriptionbusapplication.data.models.Passport
import com.example.subscriptionbusapplication.data.models.Status
import com.example.subscriptionbusapplication.data.models.SubscribeResult
import com.example.subscriptionbusapplication.data.models.SubscriptionAllDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionX
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
import retrofit2.Response
import javax.inject.Inject

class UserManagementRepositoryImp @Inject constructor(
    val subscriptionAPI: SubscriptionAPI,
    private val imageResolveAPI: ImageResolveAPI
) : UserManagement {

    private val TAG = "UserManagementRepository"

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

    override fun login(
        email: String,
        password: String,
        deviceName: String,
        appId: String
    ): Flow<AppResponse<AccessTokenModel?>> = flow {

        emit(AppResponse.Loading(isLoading = true, data = null))
        try {
            val data = subscriptionAPI.login(
                email = MultipartBody.Part.createFormData("username", email),
                password = MultipartBody.Part.createFormData("password", password),
                deviceName = MultipartBody.Part.createFormData("deviceName", deviceName),
                appId = MultipartBody.Part.createFormData("appId", appId)
            )



            if (data.code() == 403) {
                emit(
                    AppResponse.Error(
                        message = "unreachable device, stop can't do that",
                        code = 403,
                    )
                )
            } else if (data.code() == 310) {
                emit(
                    AppResponse.Error(
                        message = "email not confirmed",
                        code = 410,
                    )
                )
            } else if (data.code() == 401) {
                emit(
                    AppResponse.Error(
                        message = "email, password incorrect",
                        code = 410,
                    )
                )
            } else if (data.code() == 422) {
                emit(
                    AppResponse.Error(
                        code = 422,
                        errorModel422 = Gson().fromJson(
                            data.errorBody()?.string(),
                            ErrorModel422::class.java
                        ),
                        message = "incorrect data provided"
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


    override fun loadCurrentClient(
        token: String
    ): Flow<AppResponse<ClientModel?>> = flow {

        emit(AppResponse.Loading(isLoading = true, data = null))
        try {

            val fullToken = "Bearer $token"
            val data = subscriptionAPI.loadCurrentClient(fullToken)

            if (data.code() == 401) {
                emit(
                    AppResponse.Error(
                        message = "invalid token login again please",
                        code = 401,
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

    override fun loadSubscriptions(token: String): Flow<AppResponse<List<SubscriptionDetails>?>> =
        flow {

            emit(AppResponse.Loading(isLoading = true, data = null))
            try {

                val fullToken = "Bearer $token"
                val data = subscriptionAPI.loadSubscriptions(fullToken)

                when (data.code()) {
                    401 -> {
                        emit(
                            AppResponse.Error(
                                message = "invalid token login again please",
                                code = 401,
                            )
                        )
                    }

                    200 -> {
                        emit(AppResponse.Success(data.body()!!))
                    }

                    else -> {
                        emit(AppResponse.Error(message = data.message(), code = data.code()))
                    }
                }

            } catch (e: IOException) {
                // for all kind of network, socket error (timeout, connection error, write to closed socket)
                emit(AppResponse.Error(message = e.localizedMessage, code = -1))
            } catch (e: HttpException) {
                // for all kind for server error
                emit(AppResponse.Error(message = e.localizedMessage, code = -2))
            }
        }

    override fun loadSubscription(
        token: String,
        subscriptionId: Int
    ): Flow<AppResponse<SubscriptionAllDetails?>> = flow {

        emit(AppResponse.Loading(isLoading = true, data = null))
        val tokenCorrect = "Bearer $token"
        try {

            val response = subscriptionAPI.loadSubscriptionById(tokenCorrect, subscriptionId)

            when (response.code()) {
                401 -> {
                    emit(
                        AppResponse.Error(
                            code = 401,
                            message = "unauthorized request"
                        )
                    ) // it may due the session is expired
                }

                404 -> {
                    emit(AppResponse.Error(code = 404, message = "currently resource not exists"))
                }

                200 -> {
                    emit(AppResponse.Success(data = response.body()))
                }

                else -> {

                    emit(
                        AppResponse.Error(
                            code = response.code(),
                            message = response.message() ?: "unexpected error just done"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -1))
        } catch (e: HttpException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -2))
        }


    }

    override fun subscribe(
        token: String,
        subscriptionId: Int
    ): Flow<AppResponse<SubscribeResult?>> = flow {

        emit(AppResponse.Loading(isLoading = true, data = null))
        val tokenCorrect = "Bearer $token"
        try {
            val response = subscriptionAPI.subscribe(tokenCorrect, subscriptionId)
            when (response.code()) {
                401 -> {
                    emit(
                        AppResponse.Error(
                            code = 401,
                            message = "unauthorized request"
                        )
                    ) // it may due the session is expired
                }

                404 -> {
                    emit(AppResponse.Error(code = 404, message = "currently resource not exists"))
                }

                200 -> {
                    emit(AppResponse.Success(data = response.body()))
                }

                else -> {

                    emit(
                        AppResponse.Error(
                            code = response.code(),
                            message = response.message() ?: "unexpected error just done"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -1))
        } catch (e: HttpException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -2))
        }


    }


    override fun getSubscriptionXDetails(
        token: String,
        subscriptionXId: Int
    ): Flow<AppResponse<SubscriptionX?>> = flow {

        emit(AppResponse.Loading(isLoading = true, data = null))
        val tokenCorrect = "Bearer $token"
        try {
            val response = subscriptionAPI.subscriptionXById(tokenCorrect, subscriptionXId)
            when (response.code()) {
                401 -> {
                    emit(
                        AppResponse.Error(
                            code = 401,
                            message = "unauthorized request"
                        )
                    ) // it may due the session is expired
                }

                404 -> {
                    emit(AppResponse.Error(code = 404, message = "currently resource not exists"))
                }

                200 -> {
                    emit(AppResponse.Success(data = response.body()))
                }

                else -> {

                    emit(
                        AppResponse.Error(
                            code = response.code(),
                            message = response.message() ?: "unexpected error just done"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -1))
        } catch (e: HttpException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -2))
        }


    }

    override fun disconnect(token: String): Flow<AppResponse<Status?>> = flow {
        emit(AppResponse.Loading(isLoading = true, data = null))
        val tokenCorrect = "Bearer $token"
        try {
            val response = subscriptionAPI.disconnect(tokenCorrect)
            when (response.code()) {

                200 -> {
                    emit(AppResponse.Success(data = response.body()))
                }

                else -> {

                    emit(
                        AppResponse.Error(
                            code = response.code(),
                            message = response.message() ?: "unexpected error just done"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -1))
        } catch (e: HttpException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -2))
        }
    }

    override fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): Flow<AppResponse<Status?>> = flow {
        emit(AppResponse.Loading(isLoading = true, data = null))
        val tokenCorrect = "Bearer $token"
        try {
            val response = subscriptionAPI.changePassword(
                tokenCorrect,
                newPassword = newPassword,
                oldPassword = oldPassword
            )
            when (response.code()) {

                403 -> {
                    // password not correct
                    emit(AppResponse.Error(message = "incorrect password", code = 403))
                }

                200 -> {
                    // password update | password not changed at all
                    emit(AppResponse.Success(data = response.body()))
                }

                else -> {
                    // unexpected error
                    emit(
                        AppResponse.Error(
                            code = response.code(),
                            message = response.message() ?: "unexpected error just done"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -1))
        } catch (e: HttpException) {
            emit(AppResponse.Error(message = e.localizedMessage, code = -2))
        }
    }

    override fun sendForgetPasswordRequest(email: String): Flow<AppResponse<Status?>> {


        return flow<AppResponse<Status?>> {
            emit(AppResponse.Loading(isLoading = true, data = null))


            try {
                val response = subscriptionAPI.forgetPasswordRequest(email)

                when (response.code()) {
                    200 -> {
                        emit(AppResponse.Success(data = response.body()!!))
                    }

                    400 -> {
                        emit(
                            AppResponse.Error(
                                message = "email not found, check your email",
                                code = 400
                            )
                        )
                    }

                    else -> {

                        emit(
                            AppResponse.Error(
                                message = response.message() ?: "unexpected error happened",
                                code = response.code()
                            )
                        )
                    }
                }
            } catch (e: IOException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "connection error",
                        code = -1
                    )
                )
            } catch (e: HttpException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "server refuse request",
                        code = -2
                    )
                )
            }
        }

    }

    override fun sendForgetPasswordChange(
        email: String,
        passport: String,
        newPassword: String
    ): Flow<AppResponse<Status?>> {


        return flow {
            emit(AppResponse.Loading(isLoading = true, data = null))


            try {
                val response = subscriptionAPI.forgetPasswordChange(passport, email, newPassword)

                when (response.code()) {
                    200 -> {
                        emit(AppResponse.Success(data = response.body()))
                    }

                    422 -> {
                        emit(
                            AppResponse.Error(
                                message = "password is too easy to guess",
                                code = 422
                            )
                        )
                    }

                    401 -> {
                        emit(
                            AppResponse.Error(
                                message = "passport expired redo the process",
                                code = 400
                            )
                        )
                    }

                    else -> {

                        emit(
                            AppResponse.Error(
                                message = response.message() ?: "unexpected error happened",
                                code = response.code()
                            )
                        )
                    }
                }
            } catch (e: IOException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "connection error",
                        code = -1
                    )
                )
            } catch (e: HttpException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "server refuse request",
                        code = -2
                    )
                )
            }
        }
    }


    override fun sendForgetPasswordRequestCode(
        email: String,
        code: String
    ): Flow<AppResponse<Passport?>> {


        return flow {
            emit(AppResponse.Loading(isLoading = true, data = null))


            try {
                val response = subscriptionAPI.forgetPasswordTryCode(code = code, email = email)

                when (response.code()) {
                    200 -> {
                        emit(AppResponse.Success(data = response.body()!!))
                    }

                    401 -> {
                        emit(
                            AppResponse.Error(
                                message = "code expired",
                                code = 400
                            )
                        )
                    }

                    403 -> {
                        emit(
                            AppResponse.Error(
                                message = "code not correct",
                                code = 400
                            )
                        )
                    }

                    else -> {

                        emit(
                            AppResponse.Error(
                                message = response.message() ?: "unexpected error happened",
                                code = response.code()
                            )
                        )
                    }
                }
            } catch (e: IOException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "connection error",
                        code = -1
                    )
                )
            } catch (e: HttpException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "server refuse request",
                        code = -2
                    )
                )
            }
        }

    }

    override fun checkTokenValidation(token: String): Flow<AppResponse<Status?>> {


        return flow<AppResponse<Status?>> {
            emit(AppResponse.Loading(isLoading = true, data = null))
            val correctToken = "Bearer $token"

            try {
                val response = subscriptionAPI.checkTokenValidation(correctToken)

                when (response.code()) {
                    200 -> {
                        emit(AppResponse.Success(data = response.body()!!))
                    }

                    401 -> {
                        emit(
                            AppResponse.Error(
                                message = "email not found, check your email",
                                code = 400
                            )
                        )
                    }

                    else -> {

                        emit(
                            AppResponse.Error(
                                message = response.message() ?: "unexpected error happened",
                                code = response.code()
                            )
                        )
                    }
                }
            } catch (e: IOException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "connection error",
                        code = -1
                    )
                )
            } catch (e: HttpException) {
                emit(
                    AppResponse.Error(
                        message = e.localizedMessage ?: "server refuse request",
                        code = -2
                    )
                )
            }
        }
    }
}