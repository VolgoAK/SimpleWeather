package com.volgoak.simpleweather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.App
import com.volgoak.simpleweather.MVP
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.utils.getIconUrl
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), MVP.View {

    @Inject
    lateinit var presenter: MVP.Presenter

    lateinit var adapter: RvAdapter

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1033
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        (application as App).component.inject(this)

        adapter = RvAdapter()
        rvForecast.layoutManager = GridLayoutManager(this, 3)
        rvForecast.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.requestWeather()
    }

    override fun onPause() {
        super.onPause()
        presenter.setView(null)
    }

    override fun setWeather(weather: ReadableWeather) {
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

    override fun showError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setForecast(forecastList: List<DayForecast>) {
        //take min and max temp from forecast, cause it incorrect in the current weather
        if(forecastList.isNotEmpty()) {
            val todayMin = forecastList[0].min
            val todayMax = forecastList[0].max

            tvMinTemp.text = getString(R.string.temp_format, todayMin)
            tvMaxTemp.text = getString(R.string.temp_format, todayMax)
        }
        adapter.setData(forecastList)
    }

    override fun askLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == LOCATION_PERMISSION_REQUEST && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.requestWeather()
        }
    }
}
