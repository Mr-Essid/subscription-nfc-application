package com.example.subscriptionbusapplication.helpers

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.subscriptionbusapplication.data.models.SubscribeResult
import com.example.subscriptionbusapplication.data.models.SubscribeReturnModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonEncoder

object CustomNavTypes {

    object DashboardNavType : NavType<SubscribeResult?>(
        true
    ) {
        override fun get(bundle: Bundle, key: String): SubscribeResult? {
            val objAsString = bundle.getString(key)
            objAsString?.let {
                return Json.decodeFromString(it)
            }
            return null
        }

        override fun parseValue(value: String): SubscribeResult {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: SubscribeResult?): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: SubscribeResult?) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}


