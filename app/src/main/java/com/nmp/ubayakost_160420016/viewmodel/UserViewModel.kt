package com.nmp.ubayakost_160420016.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmp.ubayakost_160420016.model.User
import com.nmp.ubayakost_160420016.util.SharedPreferencesProvider
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<User>()

    private val TAG = "ubayaKostUserTAG"
    private var queue: RequestQueue? = null

    private val sharedPreferences = SharedPreferencesProvider(application.applicationContext)

    // Login Function - Used to handle login process
    fun login(username: String, password: String, onSuccess: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/login.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    val data = obj.getString("data")
                    val result = Gson().fromJson(data, User::class.java)
                    user.value = result

                    sharedPreferences.sessionLogin(username)
                    onSuccess(true)
                } else {
                    onSuccess(false)
                }
            },
            {
                Log.e("showvolley", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                return mutableMapOf(
                    "username" to username,
                    "password" to password
                )
            }
        }
        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }

    // Check if user isLoggedOn or not
    fun isLogin(): Boolean = sharedPreferences.isLogin()
}