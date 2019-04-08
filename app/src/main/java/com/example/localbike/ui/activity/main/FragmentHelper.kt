package com.example.localbike.ui.activity.main

import androidx.fragment.app.FragmentManager
import com.example.localbike.R
import com.example.localbike.ui.fragment.stationdetails.StationDetailsFragment
import com.example.localbike.ui.fragment.stationlist.StationListFragment

class FragmentHelper(val fragmentManager: FragmentManager, val mainFrame: Int) {
    fun getBackStackEntryCount(): Int {
        return fragmentManager.backStackEntryCount
    }

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun clearBackStack() {
        for (i in 0 until fragmentManager.backStackEntryCount)
            fragmentManager.popBackStack()
    }

    fun setStationListFragment() {
        val fragment = StationListFragment.newInstance()
        fragmentManager.beginTransaction().replace(mainFrame, fragment).commit()
    }

    fun setDetailsFragment(stationId: String) {
        fragmentManager.beginTransaction()
            .addToBackStack("")
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            .replace(mainFrame, StationDetailsFragment.newInstance(stationId))
            .commit()
    }
}