package com.doordash.doordashlite.model

import com.google.gson.annotations.SerializedName

data class RestaurantListResponse (
    @SerializedName("stores") val restaurants: List<Restaurant>
)