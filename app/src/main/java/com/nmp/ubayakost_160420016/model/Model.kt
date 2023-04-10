package com.nmp.ubayakost_160420016.model

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
)

data class Kost(
    val id: Int,
    val name: String,
    val address: String,
    val distance: Int,
    @SerializedName("pricepermonth")
    val pricePerMonth: Double,
    val capacity: Int,
    val bathroom: Int,
    val rating: Float,
    @SerializedName("main_photo")
    val mainPhoto: String,
    @SerializedName("types_id")
    val types: String,
    @SerializedName("bathroom_types_id")
    val bathroomType: String,
    val owner: String
)