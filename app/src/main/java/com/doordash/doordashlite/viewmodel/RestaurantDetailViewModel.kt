package com.doordash.doordashlite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.doordash.doordashlite.model.RestaurantDetail

class RestaurantDetailViewModel(private val repository: RestaurantRepository) : ViewModel() {

    val restaurantDetail: LiveData<RestaurantDetail>
        get() {
            return repository._restaurantDetail
        }

    val loadingRestaurantDetail: LiveData<Boolean>
        get() {
            return repository._loadingRestaurantDetail
        }

    val connectionError: LiveData<Boolean>
        get() {
            return repository._connectionError
        }

    fun fetchRestaurantDetail(id: Int) {
        repository.fetchRestaurantDetail(id)
    }
}