package com.example.localbike.ui.fragment.stationlist


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

import com.example.localbike.R
import com.example.localbike.ui.activity.main.MainActivity
import com.example.localbike.ui.fragment.StandardFragment
import javax.inject.Inject

class StationListFragment : StandardFragment()
    , StationListView {

    companion object {
        fun newInstance() = StationListFragment()
    }

    @Inject
    lateinit var presenter: StationListPresenter
    @BindView(R.id.progressView)
    lateinit var progress: View
    @BindView(R.id.bikeList)
    lateinit var listView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_station_list, container, false)
        unbinder = ButterKnife.bind(this, layout)
        presenter.restoreState(savedInstanceState)
        return layout
    }

    override fun onResume() {
        super.onResume()
        presenter.initView()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.saveState(savedInstanceState)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun getRecyclerView(): RecyclerView {
        return listView
    }

    override fun getViewContext(): Context? {
        return context
    }

    override fun setDetailsFragment(stationId: String) {
        (activity as MainActivity?)?.setDetailsFragment(stationId)
    }

    override fun informDownloadError() {
        context?.let {
            Toast.makeText(it, R.string.toast_downloading_data_error, Toast.LENGTH_LONG).show()
        }
    }
}
