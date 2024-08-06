package com.example.subscriptionbusapplication

import com.example.subscriptionbusapplication.data.models.ErrorModel422

sealed class AppResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val isLoading: Boolean = false,
    val errorModalModel422: ErrorModel422? = null
) {
    class Success<T>(
        data: T? = null,
        isLoading: Boolean = false
    ) : AppResponse<T>(data, isLoading = isLoading)

    class Error<T>(
        message: String? = null,
        data: T? = null,
        code: Int?,
        isLoading: Boolean = false,
        errorModel422: ErrorModel422? = null
    ) : AppResponse<T>(
        message = message,
        data = data,
        code = code,
        isLoading = isLoading,
        errorModalModel422 = errorModel422
    )


    class Loading<T>(
        isLoading: Boolean = true,
        data: T?
    ) : AppResponse<T>(isLoading = true, data = data)
}


fun fromStatusCodeToMessage(statusCode: Int): String = when (statusCode) {
    400 -> "wrong data selection, please make sure the data is straightforward"
    401 -> "token expired, login again to have full access to our services"
    403 -> "access forbidden, currently you have no right to this resource"
    500 -> "same things went wrong, please feel free to contact us"
    -1 -> "internet error, check your internet connection"
    -2 -> "server currently down, the server will go up later"
    else -> "we are sorry unexpected error occur, error code $statusCode"
}