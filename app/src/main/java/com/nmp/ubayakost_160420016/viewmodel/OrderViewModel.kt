package com.nmp.ubayakost_160420016.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmp.ubayakost_160420016.model.Order
import com.nmp.ubayakost_160420016.util.SharedPreferencesProvider
import org.json.JSONObject

class OrderViewModel(application: Application): AndroidViewModel(application) {
    val orderLiveData = MutableLiveData<ArrayList<Order>>()

    private val TAG = "ubayaKostOrderTAG"
    private var queue: RequestQueue? = null

    private val sharedPreferences = SharedPreferencesProvider(application.applicationContext)

    // Fetch Method - Used to fetch riwayat data from database using api call
    fun fetch(type: String, onCompletion: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/get_all_order.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    val data = obj.getString("data")
                    val mType = object : TypeToken<ArrayList<Order>>() { }.type
                    val result = Gson().fromJson<ArrayList<Order>>(data, mType)
                    orderLiveData.value = result
                    onCompletion(true)
                }
            },
            {
                Log.e("showvolley", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                return mutableMapOf(
                    "username" to sharedPreferences.getUsername(),
                    "type" to type
                )
            }
        }

        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }
}