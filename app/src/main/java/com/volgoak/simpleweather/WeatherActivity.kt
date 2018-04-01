package com.volgoak.simpleweather

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.databinding.ActivityWeatherBinding
import com.volgoak.simpleweather.utils.getIconUrl

import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), MVP.View {

    @Inject
    lateinit var presenter : MVP.Presenter

    lateinit var binding : ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        (application as App).component.inject(this)
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
        binding.tvCityName.text = weather.city
        binding.tvTemp.text = "Temp ${weather.temp}"
        Picasso.get()
                .load(getIconUrl(weather.icon))
                .into(binding.ivWeather)
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
