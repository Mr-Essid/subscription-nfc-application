package com.example.subscriptionbusapplication

import android.app.Application
import android.content.Context
import javax.inject.Inject

class SessionManagement @Inject constructor(
    context: Application
) {
    private val TOKEN_NAME = "TOKEN_NAME"
    private val SHARED_PREF_NAME = "SESSION"
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)


    fun putToken(tokenValue: String): Boolean {

        return sharedPreferences.edit().putString(TOKEN_NAME, tokenValue).commit()
    }

    fun getToken(): String {
        return sharedPreferences.getString(TOKEN_NAME, "") ?: ""
    }


    fun putValue(name: String, value: String): Boolean {

        return sharedPreferences.edit().putString(name, value).commit()
    }

    fun getValue(name: String): String {
        return sharedPreferences.getString(name, "") ?: ""
    }


    fun clearToken(): Boolean {
        return sharedPreferences.edit().remove(TOKEN_NAME).commit()
    }


}