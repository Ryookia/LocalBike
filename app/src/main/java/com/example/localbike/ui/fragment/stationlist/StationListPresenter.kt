package com.example.localbike.ui.fragment.stationlist

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localbike.data.entity.StationEntity
import com.example.localbike.location.LocationProvider
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

const val STATE_SCROLL_POSITION = "stateScroll"

class StationListPresenter(
    private val stationListModel: StationListModel,
    private val locationProvider: LocationProvider,
    private val view: StationListView
) {


    var listAdapter: StationListAdapter? = null
    var stationList: List<StationEntity>? = null
    private var listScrollPosition = 0
    private var isStopped = false
    var elementSelectObserver: Observer<Int> = generateElementObserver()

    fun initView() {
        isStopped = false
        view.showProgress()
        stationListModel.getStationList(generateStationObserver())
    }

    fun saveState(bundle: Bundle?) {
        bundle?.putInt(STATE_SCROLL_POSITION, listScrollPosition)
    }

    fun restoreState(bundle: Bundle?) {
        listScrollPosition = bundle?.getInt(STATE_SCROLL_POSITION) ?: listScrollPosition
    }

    fun onStart() {
        isStopped = false
    }

    fun onStop() {
        isStopped = true
    }

    private fun generateStationObserver(): Observer<List<StationEntity>> {
        return object : Observer<List<StationEntity>> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(stationList: List<StationEntity>) {
                onListFetched(stationList)
            }

            override fun onError(e: Throwable) {
                if (isStopped) return
                view.informDownloadError()
                view.hideProgress()
            }
        }
    }

    private fun onListFetched(stationList: List<StationEntity>) {
        view.hideProgress()
        this@StationListPresenter.stationList = stationList
        listAdapter = StationListAdapter(
            stationList,
            locationProvider,
            elementSelectObserver
        )
        view.getViewContext()?.let {
            initRecyclerView(it)
        }
    }

    private fun initRecyclerView(context: Context) {
        val recyclerView = view.getRecyclerView()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = listAdapter
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(listScrollPosition)
        recyclerView.scrollTo(0, listScrollPosition)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                listScrollPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
        })
    }

    private fun generateElementObserver(): Observer<Int> {
        return object : Observer<Int> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(position: Int) {
                val id = stationList?.get(position)?.id
                id?.let {
                    view.setDetailsFragment(it)
                }
            }

            override fun onError(e: Throwable) {
            }
        }
    }
}