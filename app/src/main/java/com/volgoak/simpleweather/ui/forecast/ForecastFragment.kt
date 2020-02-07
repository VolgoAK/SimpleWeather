package com.volgoak.simpleweather.ui.forecast

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.utils.getIconUrl
import com.volgoak.simpleweather.utils.hide
import com.volgoak.simpleweather.utils.observeSafe
import com.volgoak.simpleweather.utils.show
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : Fragment(R.layout.activity_weather),
        FlexibleAdapter.OnItemClickListener {

    private val viewModel by viewModel<ForecastViewModel>()

    lateinit var adapter: RvAdapter
    private val forecastAdapter = FlexibleAdapter(emptyList(), this)

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1033
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RvAdapter()
        rvForecast.layoutManager = GridLayoutManager(requireContext(), 3)
        rvForecast.adapter = adapter

        rvForecast.apply {
            layoutManager = GridLayoutManager(requireContext(), 3).apply {
                spanSizeLookup = createSpanSizeLookup()
            }
            adapter = forecastAdapter
        }

        viewModel.stateLD.observeSafe(this, ::onNewState)
    }

    private fun createSpanSizeLookup() = object: GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when(forecastAdapter.getItem(position)) {
                is CurrentWeatherItem -> 3
                else -> 1
            }
        }
    }

    private fun onNewState(state: ForecastScreenState) {
        when (state) {
            is ForecastScreenState.WeatherLoaded -> onWeatherLoaded(state.weather, state.forecast)
            is ForecastScreenState.Error -> {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
            is ForecastScreenState.Loading -> progress.show()
            is ForecastScreenState.LocationPermissionRequired -> askLocationPermission()
        }
    }

    private fun onWeatherLoaded(weather: ReadableWeather, forecastList: List<DayForecast>) {
        progress.hide()
        val items = mutableListOf<IFlexible<*>>(CurrentWeatherItem(weather))
        items.addAll(forecastList.map { DayForecastItem(it) })
        forecastAdapter.updateDataSet(items)
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return true
    }

    private fun askLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.onPermissionResult(true)
        }
    }
}
