package com.doordash.doordashlite.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doordash.doordashlite.R
import com.doordash.doordashlite.fragment.RestaurantListFragment
import com.doordash.doordashlite.viewmodel.RestaurantListViewModel
import com.doordash.doordashlite.viewmodel.ViewModelFactory

class RestaurantListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_list_activity)
        supportActionBar?.title = getString(R.string.discover_title)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, RestaurantListFragment.newInstance())
                .commit()
        }
    }

    fun obtainViewModel(): RestaurantListViewModel {
        return ViewModelProvider(
            this,
            ViewModelFactory.getInstance()
        ).get(RestaurantListViewModel::class.java)
    }
}