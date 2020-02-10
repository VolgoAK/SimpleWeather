package com.volgoak.simpleweather.ui.forecast.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.utils.getIconUrl
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_current_weather.view.*


class CurrentWeatherItem(val weather: ReadableWeather) : AbstractFlexibleItem<CurrentWeatherItem.ViewHolder>() {

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
                                holder: ViewHolder,
                                position: Int,
                                payloads: MutableList<Any>) {
        with(holder) {
            val context = itemView.context
            tvCityName.text = weather.city
            tvTemp.text = context.getString(R.string.temp_format, weather.temp)
            tvWeatherDescription.text = weather.description

            tvMinTemp.text = context.getString(R.string.temp_format, weather.min)
            tvMaxTemp.text = context.getString(R.string.temp_format, weather.max)

            tvUnit.text = "C"

            Picasso.get()
                    .load(getIconUrl(weather.icon))
                    .into(ivWeather)
        }
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) = ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is CurrentWeatherItem
            && other.weather == weather

    override fun getLayoutRes() = R.layout.item_current_weather

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?)
        : FlexibleViewHolder(view, adapter) {
        val tvCityName = view.tvCityName
        val tvTemp = view.tvTemp
        val tvWeatherDescription = view.tvWeatherDescription
        val tvMinTemp = view.tvMinTemp
        val tvMaxTemp = view.tvMaxTemp
        val tvUnit = view.tvUnit
        val ivWeather = view.ivWeather
    }
}