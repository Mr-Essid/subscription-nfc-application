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

class RegisterUseCase @Inject constructor(
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
            firstname = MultipartBody.Part.createFormData("firstname", dataFlowRapper.firstname!!),
            lastname = MultipartBody.Part.createFormData("lastname", lastname!!),
            email = MultipartBody.Part.createFormData("email", dataFlowRapper.email!!),
            password = MultipartBody.Part.createFormData(
                "password",
                dataFlowRapper.password!!
            ),
            appId = MultipartBody.Part.createFormData(
                "appId",
                dataFlowRapper.appId!!
            ),
            deviceId = MultipartBody.Part.createFormData(
                "deviceName",
                dataFlowRapper.deviceName!!
            ),
            phoneNumber = MultipartBody.Part.createFormData(
                "phoneNumber",
                dataFlowRapper.phoneNumber!!
            ),
            multipartBody = image
        )


    }

}