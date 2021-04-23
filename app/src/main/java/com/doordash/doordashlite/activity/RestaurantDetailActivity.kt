package com.doordash.doordashlite.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.doordash.doordashlite.R
import com.doordash.doordashlite.fragment.RestaurantDetailFragment
import com.doordash.doordashlite.viewmodel.RestaurantDetailViewModel
import com.doordash.doordashlite.viewmodel.ViewModelFactory


class RestaurantDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_detail_activity)
        setUpTitleBar()
        val id = intent.getIntExtra(getString(R.string.key_business_id), INVALID_BUSINESS_ID)
        if (savedInstanceState == null && id != INVALID_BUSINESS_ID) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, RestaurantDetailFragment.newInstance(id))
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun obtainViewModel(): RestaurantDetailViewModel {
        return ViewModelProvider(
            this,
            ViewModelFactory.getInstance()
        ).get(RestaurantDetailViewModel::class.java)
    }

    private fun setUpTitleBar() {
        supportActionBar?.title = getString(R.string.restaurant_detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val INVALID_BUSINESS_ID = -1
    }
}