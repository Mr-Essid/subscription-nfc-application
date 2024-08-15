package com.example.subscriptionbusapplication.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle


class MainTask : HostApduService() {
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        
        TODO("Not yet implemented")
    }

    override fun onDeactivated(reason: Int) {
        TODO("Not yet implemented")
    }
}