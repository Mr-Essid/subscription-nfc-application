package com.example.subscriptionbusapplication.prisentation.ui.states

import com.example.subscriptionbusapplication.data.models.ClientModel

data class ClientState(
    var firstName: String,
    var lastName: String,
    var email: String,
    var wallet: Double,
    var imagePath: String,
    var phoneNumber: String,
    var deviceName: String,
    var appId: String
) {
    companion object {}
}

fun ClientState.Companion.fromUserModel(clientModel: ClientModel): ClientState {
    return ClientState(
        firstName = clientModel.firstName,
        lastName = clientModel.lastName,
        email = clientModel.email,
        wallet = clientModel.wallet,
        deviceName = clientModel.deviceName,
        appId = clientModel.appId,
        imagePath = clientModel.imagePath,
        phoneNumber = clientModel.phoneNumber
    )
}

