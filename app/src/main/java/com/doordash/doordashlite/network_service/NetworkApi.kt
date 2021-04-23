package com.doordash.doordashlite.network_service

import com.doordash.doordashlite.model.RestaurantDetail
import retrofit2.http.GET
import retrofit2.http.Query
import com.doordash.doordashlite.model.RestaurantListResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Path

interface NetworkApi {

    @GET("/v1/store_feed/")
    fun fetchRestaurantList(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<RestaurantListResponse>

    @GET("/v2/restaurant/{id}")
    fun fetchRestaurantDetail(@Path("id") id: Int): Observable<RestaurantDetail>
}