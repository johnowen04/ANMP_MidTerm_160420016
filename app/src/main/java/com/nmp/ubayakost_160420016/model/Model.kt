package com.nmp.ubayakost_160420016.model

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
)