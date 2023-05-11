package com.nmp.ubayakost_160420016.util

import android.util.Log
import android.widget.ImageView
import com.google.gson.Gson
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

fun ImageView.loadImage(url: String?) {
    Picasso.get()
        .load(url)
        .resize(500, 320)
        .centerCrop()
        .error(R.drawable.ic_baseline_error_24)
        .into(this, object : Callback {
            override fun onSuccess() {
                Log.d("Success", "Image successfully loaded")
            }

            override fun onError(e: Exception?) {
                Log.d("Failed", "Could not load image")
            }

        })
}

fun String.toUser(): User {
    if (this.isBlank())
        return User("empty", "empty", "empty", "empty")
    return Gson().fromJson(this, User::class.java)
}