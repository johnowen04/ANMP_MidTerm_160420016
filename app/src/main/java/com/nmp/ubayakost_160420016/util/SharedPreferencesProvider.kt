package com.nmp.ubayakost_160420016.util

import android.content.Context
import android.content.SharedPreferences

private const val PREF_NAME = "UbayaKost"
private const val PREF_IS_LOGIN = "PREF_IS_LOGIN"
private const val PREF_USERNAME = "PREF_USERNAME"

class SharedPreferencesProvider(private val appContext: Context) {

    private val sharedPreferences: SharedPreferences
        get() = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getUsername(): String = sharedPreferences.getString(PREF_USERNAME, "").toString()

    fun isLogin(): Boolean = sharedPreferences.getBoolean(PREF_IS_LOGIN, false)

    fun sessionLogin(username: String){
        sharedPreferences.edit().apply {
            putString(PREF_USERNAME, username)
            putBoolean(PREF_IS_LOGIN, true)
        }.apply()
    }

    fun logout(){
        sharedPreferences.edit().clear().apply()
    }
}