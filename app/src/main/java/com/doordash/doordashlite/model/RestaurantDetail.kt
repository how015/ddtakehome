package com.doordash.doordashlite.model

import com.google.gson.annotations.SerializedName

data class RestaurantDetail(
    val business_id: Int,
    val phone_number: String,
    val average_rating: Double,
    val offers_pickup: Boolean,
    val number_of_ratings: Int,
    val description: String,
    @SerializedName("asap_time") val delivery_time: Int,
    val cover_img_url: String,
    val address: Address,
    val price_range: Int,
    val business: Business
)