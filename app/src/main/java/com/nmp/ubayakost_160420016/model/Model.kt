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
    @SerializedName("types")
    val types: String,
    @SerializedName("bathroom_types")
    val bathroomType: String,
    val owner: String,
    @SerializedName("is_favorite")
    var isFavorite: Boolean
)

data class Order(
    @SerializedName("property_data")
    val kost: Kost,
    @SerializedName("created_at")
    val dateBooked: String,
    @SerializedName("date_until")
    val dateStayedUntil: String,
    @SerializedName("payment_method")
    val paymentMethod: String
)