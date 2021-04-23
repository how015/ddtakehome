package com.doordash.doordashlite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doordash.doordashlite.model.Restaurant
import com.doordash.doordashlite.model.RestaurantDetail
import com.doordash.doordashlite.network_service.RxApiService
import com.doordash.doordashlite.viewmodel.RestaurantDetailViewModel
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


class RestaurantDetailViewModelTest {

    @Mock
    private lateinit var rxApiService: RxApiService

    private lateinit var repository: RestaurantRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantDetailViewModel

    private var mockedRestaurants = mutableListOf<Restaurant>()

    @Before
    fun setUp() {
        initMocks(this)
        repository = RestaurantRepository(rxApiService)
        viewModel = RestaurantDetailViewModel(repository)

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
    fun shouldUpdateRestaurantDetailLiveDataOnSuccess() {
        val mockedRestaurantDetail = mock(RestaurantDetail::class.java)
        `when`(rxApiService.fetchRestaurantDetail(
            any(Int::class.java)
        )).thenReturn(
            Observable.just(mockedRestaurantDetail)
        )

        val restaurantDetailObserver = viewModel.restaurantDetail.testObserver()
        viewModel.fetchRestaurantDetail(1001)
        assertEquals(listOf(mockedRestaurantDetail), restaurantDetailObserver.observedValues)
    }

    @Test
    fun shouldUpdateLoadingRestaurantDetailLiveDataWhenLoading() {
        val mockedStoreDetail = mock(RestaurantDetail::class.java)
        `when`(rxApiService.fetchRestaurantDetail(
            any(Int::class.java)
        )).thenReturn(
            Observable.just(mockedStoreDetail)
        )

        val loadingRestaurantDetailObserver = viewModel.loadingRestaurantDetail.testObserver()
        viewModel.fetchRestaurantDetail(1001)
        assertEquals(listOf(true, false), loadingRestaurantDetailObserver.observedValues)
    }

    @Test
    fun shouldUpdateConnectionErrorLiveDataOnFailure() {
        `when`(rxApiService.fetchRestaurantDetail(
            any(Int::class.java)
        )).thenReturn(
            Observable.error(Throwable())
        )

        val connectionErrorObserver = viewModel.connectionError.testObserver()
        viewModel.fetchRestaurantDetail(1001)
        assertEquals(listOf(false, true), connectionErrorObserver.observedValues)
    }
}