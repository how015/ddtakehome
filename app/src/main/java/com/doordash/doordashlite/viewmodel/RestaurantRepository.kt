package com.doordash.doordashlite.viewmodel

import androidx.lifecycle.MutableLiveData
import com.doordash.doordashlite.model.*
import com.doordash.doordashlite.network_service.RxApiService


class RestaurantRepository(private val rxApi: RxApiService) {

    val _restaurantDetail = MutableLiveData<RestaurantDetail>()
    val _restaurantList = MutableLiveData<List<Restaurant>>()
    val _loadingRestaurantDetail = MutableLiveData<Boolean>()
    val _connectionError = MutableLiveData<Boolean>(false)

    fun fetchRestaurantList(lat: Double, lng: Double, offset: Int, limit: Int) {
        rxApi.fetchRestaurantList(lat, lng, offset, limit).subscribe({
            processRestaurantList(it)
        }, {
            _connectionError.postValue(true)
        })
    }

    fun fetchRestaurantDetail(id: Int) {
        _loadingRestaurantDetail.postValue(true)
        rxApi.fetchRestaurantDetail(id).subscribe({
            processRestaurantDetail(it)
        }, {
            _connectionError.postValue(true)
            _loadingRestaurantDetail.postValue(false)
        })
    }

    private fun processRestaurantList(response: RestaurantListResponse) {
        _connectionError.postValue(false)
        _restaurantList.postValue(response.restaurants)
    }

    private fun processRestaurantDetail(response: RestaurantDetail) {
        _connectionError.postValue(false)
        _restaurantDetail.postValue(response)
        _loadingRestaurantDetail.postValue(false)
    }

    companion object {

        private var INSTANCE: RestaurantRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(RestaurantRepository::class.java) {
                INSTANCE ?: RestaurantRepository(RxApiService.getInstance())
                    .also { INSTANCE = it }
            }
    }
}