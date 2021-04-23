package com.doordash.doordashlite.network_service

import com.doordash.doordashlite.model.RestaurantDetail
import com.doordash.doordashlite.model.RestaurantListResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RxApiService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.doordash.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: NetworkApi = retrofit.create(NetworkApi::class.java)

    fun fetchRestaurantList(
        lat: Double,
        lng: Double,
        offset: Int,
        limit: Int
    ): Observable<RestaurantListResponse> {
        return api.fetchRestaurantList(lat, lng, offset, limit)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchRestaurantDetail(id: Int): Observable<RestaurantDetail> {
        return api.fetchRestaurantDetail(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private var INSTANCE: RxApiService? = null

        @JvmStatic
        fun getInstance(): RxApiService {
            if (INSTANCE == null) {
                synchronized(RxApiService::javaClass) {
                    INSTANCE = RxApiService()
                }
            }
            return INSTANCE!!
        }
    }
}