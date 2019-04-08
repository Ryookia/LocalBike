package com.example.localbike.ui.activity.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import com.example.localbike.R
import com.example.localbike.location.LocationProvider
import com.example.localbike.utils.PermissionHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 100
    }

    @Inject
    lateinit var fragmentHelper: FragmentHelper
    @Inject
    lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndAskPermission()
        initFragment()
    }

    override fun onResume() {
        super.onResume()
        locationProvider.observeLocation()
    }

    override fun onPause() {
        super.onPause()
        locationProvider.stopLocation()
    }

    override fun onBackPressed() {
        if (fragmentHelper.getBackStackEntryCount() > 0) {
            fragmentHelper.popBackStack()
            return
        }
        super.onBackPressed()
    }

    fun setDetailsFragment(stationId: String) {
        fragmentHelper.setDetailsFragment(stationId)
    }

    private fun initFragment() {
        if (fragmentHelper.getBackStackEntryCount() == 0)
            fragmentHelper.setStationListFragment()
    }

    private fun checkAndAskPermission() {
        if (!PermissionHelper.haveLocationPermission(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            }
        }
    }
}
