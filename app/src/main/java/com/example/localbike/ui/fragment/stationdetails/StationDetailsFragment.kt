package com.example.localbike.ui.fragment.stationdetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.localbike.R
import com.example.localbike.ui.fragment.StandardFragment
import com.example.localbike.utils.ResourceManager
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

class StationDetailsFragment : StandardFragment(),
    StationDetailsView, ResourceManager {

    companion object {
        private const val ARG_STATION_ID = "stationId"
        fun newInstance(stationId: String): StationDetailsFragment {
            return StationDetailsFragment().also {
                it.arguments = Bundle().also {
                    it.putString(ARG_STATION_ID, stationId)
                }
            }
        }
    }

    @BindView(R.id.mapView)
    lateinit var map: MapView
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
    @BindView(R.id.progressView)
    lateinit var progress: View

    @Inject
    lateinit var presenter: StationDetailsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_station_details, container, false)
        unbinder = ButterKnife.bind(this, layout)
        presenter.initViews(fetchStationArgument())
        return layout
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onPause() {
        super.onPause()
        presenter.onStop()
    }

    override fun initMap(fragment: SupportMapFragment) {
        fragmentManager
            ?.beginTransaction()
            ?.add(R.id.mapView, fragment)
            ?.commit()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setRange(range: String) {
        if (range.isEmpty()) {
            this.range.text = range
        } else {
            this.range.text = getString(R.string.distance_template, range)
        }
    }

    override fun setBikeAvailability(bikeAvailability: String) {
        this.bikeAvailability.text = bikeAvailability
    }

    override fun setPlaceAvailability(placeAvailability: String) {
        this.placeAvailability.text = placeAvailability
    }

    override fun getMapView(): MapView {
        return map
    }

    override fun informDownloadError() {
        context?.let {
            Toast.makeText(it, R.string.toast_downloading_data_error, Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchStationArgument(): String {
        return arguments?.getString(ARG_STATION_ID) ?: throw Throwable("Fragment was created without station id")
    }
}
