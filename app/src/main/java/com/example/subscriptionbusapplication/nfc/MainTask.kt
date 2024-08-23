package com.example.subscriptionbusapplication.nfc

import android.content.Context
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import com.example.subscriptionbusapplication.Constants


class MainTask : HostApduService() {
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        val id =
            application.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
                .getInt(Constants.USERID_NAME, -1)
        // we will handle -1 in the reader level thanks!!

        return "CLIENT_ID:$id".toByteArray()
    }

    override fun onDeactivated(reason: Int) {
        println("broken pipe $reason")
    }
}