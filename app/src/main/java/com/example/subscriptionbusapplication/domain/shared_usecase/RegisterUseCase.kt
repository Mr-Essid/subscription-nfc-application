package com.example.subscriptionbusapplication.domain.shared_usecase

import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.User
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.data.repositoryImp.UserManagementRepositoryImp
import com.example.subscriptionbusapplication.prisentation.ui.DataFlowRapper
import com.example.subscriptionbusapplication.prisentation.ui.isDataCollected
import okhttp3.MultipartBody
import java.util.concurrent.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor (
    private val userManagementRepositoryImp: UserManagement
) {
    class ArgumentsNotCompleteError(
        override val message: String
    ) : IllegalArgumentException(message)


    operator fun invoke(
        dataFlowRapper: DataFlowRapper,
        image: MultipartBody.Part
    ): kotlinx.coroutines.flow.Flow<AppResponse<User?>> {

        if (!dataFlowRapper.isDataCollected()) {
            throw ArgumentsNotCompleteError("Not All Data Collected For Given Register, Check Your Data")
        }
        // prepare the model
        val lastname =
            if (dataFlowRapper.lastname.isNullOrBlank()) "${dataFlowRapper.lastname} ${dataFlowRapper.middleName}" else dataFlowRapper.lastname


        return userManagementRepositoryImp.register(
            firstname = dataFlowRapper.firstname!!,
            lastname = lastname!!,
            email = dataFlowRapper.email!!,
            password = dataFlowRapper.password!!,
            appId = dataFlowRapper.appId!!,
            deviceId = dataFlowRapper.deviceName!!,
            multipartBody = image,
            phoneNumber = dataFlowRapper.phoneNumber!!
        )


    }

}