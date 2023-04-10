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
import com.nmp.ubayakost_160420016.model.Kost
import org.json.JSONObject

class KostViewModel(application: Application): AndroidViewModel(application) {
    val kostLiveData = MutableLiveData<ArrayList<Kost>>()

    private val TAG = "ubayaKostUserTAG"
    private var queue: RequestQueue? = null

    // Fetch Method - Used to fetch properties data from database using api call
    fun fetch(onCompletion: (success: Boolean) -> Unit) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/ubayakost_api/get_all_kost.php"
        val stringRequest = StringRequest(
            Request.Method.POST, url,
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
        )

        stringRequest.apply { tag = TAG }

        queue?.add(stringRequest)
    }
}