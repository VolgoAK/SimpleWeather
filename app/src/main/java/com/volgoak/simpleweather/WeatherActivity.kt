package com.volgoak.simpleweather

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.utils.getIconUrl
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), MVP.View {

    @Inject
    lateinit var presenter: MVP.Presenter

    lateinit var adapter : RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        (application as App).component.inject(this)

        adapter = RvAdapter()
        rvForecast.layoutManager = GridLayoutManager(this,3)
        rvForecast.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.requestWeather()
        presenter.requestForecast()
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
        adapter.setData(forecastList)
    }
}
