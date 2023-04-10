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
import com.nmp.ubayakost_160420016.model.Kost
import com.nmp.ubayakost_160420016.util.SharedPreferencesProvider
import org.json.JSONObject

class KostViewModel(application: Application): AndroidViewModel(application) {
    val kostLiveData = MutableLiveData<ArrayList<Kost>>()
    val selectedKostLiveData = MutableLiveData<Kost>()

    private val TAG = "ubayaKostUserTAG"
    private var queue: RequestQueue? = null

    private val sharedPreferences = SharedPreferencesProvider(application.applicationContext)

    // Fetch Method - Used to fetch properties data from database using api call
    fun fetch(onCompletion: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/get_all_kost.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    val data = obj.getString("data")
                    val mType = object : TypeToken<ArrayList<Kost>>() { }.type
                    val result = Gson().fromJson<ArrayList<Kost>>(data, mType)
                    kostLiveData.value = result
                    onCompletion(true)
                }
            },
            {
                Log.e("showvolley", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "username" to sharedPreferences.getUsername()
                )
            }
        }

        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }

    fun fetchFavorite(onCompletion: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/get_favorite_kost.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    val data = obj.getString("data")
                    val mType = object : TypeToken<ArrayList<Kost>>() { }.type
                    val result = Gson().fromJson<ArrayList<Kost>>(data, mType)
                    kostLiveData.value = result
                    onCompletion(true)
                }
            },
            {
                Log.e("showvolley", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "username" to sharedPreferences.getUsername()
                )
            }
        }

        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }

    fun favorite(id: Int, onCompletion: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/set_favorite_kost.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    onCompletion(true)
                } else {
                    onCompletion(false)
                }
            },
            {
                Log.e("showvolley", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "username" to sharedPreferences.getUsername(),
                    "properties_id" to id.toString()
                )
            }
        }

        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }

    fun fetchDetails(id: Int, onCompletion: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/get_detail_kost.php"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            {
                Log.d("showvolley", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    val data = obj.getString("data")
                    val mType = object : TypeToken<Kost>() { }.type
                    val result = Gson().fromJson<Kost>(data, mType)
                    selectedKostLiveData.value = result
                    onCompletion(true)
                }
            },
            {
                Log.e("showvolley", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "id" to id.toString(),
                    "username" to sharedPreferences.getUsername()
                )
            }
        }

        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }
}