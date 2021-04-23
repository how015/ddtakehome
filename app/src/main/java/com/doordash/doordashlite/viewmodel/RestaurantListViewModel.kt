package com.doordash.doordashlite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.doordash.doordashlite.model.Restaurant


class RestaurantListViewModel(private val repository: RestaurantRepository) : ViewModel() {

    val restaurantList: LiveData<List<Restaurant>>
        get() {
            return repository._restaurantList
        }

    val connectionError: LiveData<Boolean>
        get() {
            return repository._connectionError
        }

    fun fetchRestaurantList(lat: Double, lng: Double, offset: Int, limit: Int) {
        repository.fetchRestaurantList(lat, lng, offset, limit)
    }
}