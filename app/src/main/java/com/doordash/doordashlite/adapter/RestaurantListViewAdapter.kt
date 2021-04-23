package com.doordash.doordashlite.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.doordash.doordashlite.R
import com.doordash.doordashlite.activity.RestaurantDetailActivity
import com.doordash.doordashlite.model.Restaurant
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class RestaurantListViewAdapter : RecyclerView.Adapter<RestaurantListViewAdapter.ViewHolder>() {

    private var restaurants: MutableList<Restaurant> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size

    fun updateRestaurantList(list: List<Restaurant>) {
        restaurants.clear()
        restaurants.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var nameView: TextView = view.findViewById(R.id.name)
        var descriptionView: TextView = view.findViewById(R.id.description)
        var etaView: TextView = view.findViewById(R.id.eta)
        var logoView: ShapeableImageView = view.findViewById(R.id.logo_img)

        fun bind(restaurant: Restaurant) {
            nameView.text = restaurant.name
            descriptionView.text = restaurant.description
            etaView.text =
                view.context.getString(R.string.eta_in_min, restaurant.status.asap_minutes_range[0])

            Picasso.get()
                .load(restaurant.cover_img_url)
                .placeholder(R.drawable.image_loading_animation)
                .error(R.drawable.image_download_error)
                .into(logoView)

            view.setOnClickListener {
                val intent = Intent(view.context, RestaurantDetailActivity::class.java)
                intent.putExtra(view.context.getString(R.string.key_business_id), restaurant.id)
                view.context.startActivity(intent)
            }
        }
    }
}