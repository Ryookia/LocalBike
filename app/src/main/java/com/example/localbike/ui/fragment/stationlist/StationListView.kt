package com.example.localbike.ui.fragment.stationlist

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

interface StationListView {
    fun showProgress()
    fun hideProgress()
    fun getRecyclerView(): RecyclerView
    fun getViewContext(): Context?
    fun setDetailsFragment(stationId: String)
    fun informDownloadError()
}