package com.volgoak.simpleweather.ui.forecast

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.utils.getIconUrl
import com.volgoak.simpleweather.utils.hide
import com.volgoak.simpleweather.utils.observeSafe
import com.volgoak.simpleweather.utils.show
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : Fragment(R.layout.activity_weather) {

    private val viewModel by viewModel<ForecastViewModel>()

    lateinit var adapter: RvAdapter

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1033
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RvAdapter()
        rvForecast.layoutManager = GridLayoutManager(requireContext(), 3)
        rvForecast.adapter = adapter

        viewModel.stateLD.observeSafe(this, ::onNewState)
    }

    private fun onNewState(state: ForecastScreenState) {
        when (state) {
            is ForecastScreenState.WeatherLoaded -> {
                progress.hide()
                setWeather(state.weather)
                setForecast(state.forecast)
            }
            is ForecastScreenState.Error -> {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
            is ForecastScreenState.Loading -> progress.show()
            is ForecastScreenState.LocationPermissionRequired -> askLocationPermission()
        }
    }

    private fun setWeather(weather: ReadableWeather) {
        tvCityName.text = weather.city
        tvTemp.text = getString(R.string.temp_format, weather.temp)
        tvWeatherDescription.text = weather.description

        tvMinTemp.text = getString(R.string.temp_format, weather.min)
        tvMaxTemp.text = getString(R.string.temp_format, weather.max)

        tvUnit.text = "C"

        Picasso.get()
                .load(getIconUrl(weather.icon))
                .into(ivWeather)
    }

    private fun setForecast(forecastList: List<DayForecast>) {
        //take min and max temp from forecast, cause it incorrect in the current weather
        if (forecastList.isNotEmpty()) {
            val todayMin = forecastList[0].min
            val todayMax = forecastList[0].max

            tvMinTemp.text = getString(R.string.temp_format, todayMin)
            tvMaxTemp.text = getString(R.string.temp_format, todayMax)
        }
        adapter.setData(forecastList)
    }

    private fun askLocationPermission() {
        requestPermissions( arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.onPermissionResult(true)
        }
    }
}
