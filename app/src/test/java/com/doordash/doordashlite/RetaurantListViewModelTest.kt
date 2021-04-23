package com.doordash.doordashlite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doordash.doordashlite.model.Restaurant
import com.doordash.doordashlite.model.RestaurantListResponse
import com.doordash.doordashlite.network_service.RxApiService
import com.doordash.doordashlite.viewmodel.RestaurantListViewModel
import com.doordash.doordashlite.viewmodel.RestaurantRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations.initMocks


class RestaurantListViewModelTest {

    @Mock
    private lateinit var rxApiService: RxApiService

    private lateinit var repository: RestaurantRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantListViewModel

    private var mockedRestaurants = mutableListOf<Restaurant>()

    @Before
    fun setUp() {
        initMocks(this)
        repository = RestaurantRepository(rxApiService)
        viewModel = RestaurantListViewModel(repository)

        setupSchedulers()
        mockedRestaurants = getMockedRestaurants()
    }

    private fun getMockedRestaurants(): MutableList<Restaurant> {
        val restaurants = mutableListOf<Restaurant>()
        for (i in 0 until 10) {
            restaurants.add(mock(Restaurant::class.java))
        }
        return restaurants
    }

    @Test
    fun shouldUpdateRestaurantListLiveDataWhenOnSuccess() {
        `when`(rxApiService.fetchRestaurantList(
            any(Double::class.java),
            any(Double::class.java),
            any(Int::class.java),
            any(Int::class.java)
        )).thenReturn(
            Observable.just(RestaurantListResponse(mockedRestaurants))
        )

        val restaurantListObserver = viewModel.restaurantList.testObserver()
        viewModel.fetchRestaurantList(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)
        assertEquals(listOf(mockedRestaurants), restaurantListObserver.observedValues)
    }

    @Test
    fun shouldUpdateConnectionErrorLiveDataWhenOnFailure() {
        `when`(rxApiService.fetchRestaurantList(
            any(Double::class.java),
            any(Double::class.java),
            any(Int::class.java),
            any(Int::class.java)
        )).thenReturn(
            Observable.error(Throwable())
        )

        val connectionErrorObserver = viewModel.connectionError.testObserver()
        viewModel.fetchRestaurantList(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)
        assertEquals(listOf(false, true), connectionErrorObserver.observedValues)
    }
}