package com.example.localbike.ui.fragment.stationlist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.localbike.R
import io.reactivex.disposables.Disposable

class StationListHolder(view: View) : RecyclerView.ViewHolder(view) {

    var disposable: Disposable? = null

    init {
        ButterKnife.bind(this, view)
    }

    @BindView(R.id.rootLayout)
    lateinit var layout: View
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.range)
    lateinit var range: TextView
    @BindView(R.id.address)
    lateinit var address: TextView
    @BindView(R.id.bikeAvailability)
    lateinit var bikeAvailability: TextView
    @BindView(R.id.placeAvailability)
    lateinit var placeAvailability: TextView

    fun setSubscription(disposable: Disposable) {
        this.disposable?.let {
            if (!it.isDisposed)
                it.dispose()
        }
        this.disposable = disposable
    }

}