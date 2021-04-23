package com.doordash.doordashlite.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.doordash.doordashlite.R
import com.doordash.doordashlite.activity.RestaurantDetailActivity
import com.doordash.doordashlite.databinding.FragmentRestaurantDetailBinding
import com.doordash.doordashlite.model.RestaurantDetail
import com.doordash.doordashlite.viewmodel.RestaurantDetailViewModel
import com.squareup.picasso.Picasso


class RestaurantDetailFragment : Fragment() {

    private lateinit var viewModel: RestaurantDetailViewModel
    private lateinit var binding: FragmentRestaurantDetailBinding
    private var restaurantId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        restaurantId = arguments?.getInt(getString(R.string.key_business_id))
        viewModel = (activity as RestaurantDetailActivity).obtainViewModel()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.restaurantDetail.observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
        restaurantId?.let {
            viewModel.fetchRestaurantDetail(it)
        }
    }

    private fun updateView(restaurantDetail: RestaurantDetail) {
        Picasso.get()
            .load(restaurantDetail.cover_img_url)
            .placeholder(R.drawable.image_loading_animation)
            .error(R.drawable.image_download_error)
            .into(binding.headerImage)

        binding.priceRange.text = "$".repeat(restaurantDetail.price_range)
    }

    companion object {
        private const val KEY_BUSINESS_ID = "BUSINESS_ID"

        @JvmStatic
        fun newInstance(id: Int): RestaurantDetailFragment {
            val fragment = RestaurantDetailFragment()
            val args = Bundle()
            args.putInt(KEY_BUSINESS_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}