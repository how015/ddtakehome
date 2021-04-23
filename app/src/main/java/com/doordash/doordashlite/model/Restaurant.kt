package com.doordash.doordashlite.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: Int,
    val cover_img_url: String,
    val header_img_url: String,
    val name: String,
    val description: String,
    val status: Status,
    val price_range: Int,
    @SerializedName("distance_from_consumer") val distance: Double,
    val menus: List<Menu>
)