package com.nmp.ubayakost_160420016.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmp.ubayakost_160420016.model.User
import com.nmp.ubayakost_160420016.util.SharedPreferencesProvider
import com.nmp.ubayakost_160420016.view.RegisterFragmentDirections
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<User>()

    private val TAG = "ubayaKostUserTAG"
    private var queue: RequestQueue? = null

    private val sharedPreferences = SharedPreferencesProvider(application.applicationContext)

    fun getUserFromSharedPref() = sharedPreferences.getUser()

    // Login Function - Used to handle login process
    fun login(username: String, password: String, onSuccess: (success: Boolean, message: String?) -> Unit) {
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

                    sharedPreferences.sessionLogin(username, data)
                    onSuccess(true, obj.getString("message"))
                } else {
                    onSuccess(false, obj.getString("message"))
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

    fun logout() = sharedPreferences.logout()

    // Register function - Used to register new user
    fun register(username: String, password: String, onSuccess: (success: Boolean, message: String?) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/register.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    onSuccess(true, obj.getString("message"))
                } else {
                    onSuccess(false, obj.getString("message"))
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
}