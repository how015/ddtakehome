package com.doordash.doordashlite.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.doordash.doordashlite.adapter.RestaurantListViewAdapter
import com.doordash.doordashlite.activity.RestaurantListActivity
import com.doordash.doordashlite.databinding.FragmentRestaurantListBinding
import com.doordash.doordashlite.viewmodel.RestaurantListViewModel


class RestaurantListFragment : Fragment() {

    private lateinit var viewModel: RestaurantListViewModel
    private lateinit var restaurantListViewAdapter: RestaurantListViewAdapter
    private lateinit var binding: FragmentRestaurantListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        restaurantListViewAdapter = RestaurantListViewAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = restaurantListViewAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as RestaurantListActivity).obtainViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.restaurantList.observe(viewLifecycleOwner, Observer {
            (restaurantListViewAdapter).updateRestaurantList(it)
        })
        viewModel.fetchRestaurantList(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, LIMIT)
    }

    companion object {
        private const val DEFAULT_LAT = 37.422740
        private const val DEFAULT_LNG = -122.139956
        private const val DEFAULT_OFFSET = 0
        private const val LIMIT = 50

        @JvmStatic
        fun newInstance() = RestaurantListFragment()
    }
}