package com.volgoak.simpleweather.ui.forecast.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.utils.dateFormat
import com.volgoak.simpleweather.utils.getDayIconUrl
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.forecast_holder.view.*


class DayForecastItem(val forecast: DayForecast) : AbstractFlexibleItem<DayForecastItem.ViewHolder>() {

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
                                holder: ViewHolder,
                                position: Int,
                                payloads: MutableList<Any>) {
        with(holder){
            tvMax.text = itemView.context.getString(R.string.temp_format, forecast.max)
            tvMin.text = itemView.context.getString(R.string.temp_format, forecast.min)

            tvDay.text = dateFormat.format(forecast.date)

            Picasso.get()
                    .load(getDayIconUrl(forecast.icon))
                    .into(icon)
        }
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) = ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is DayForecastItem
            && other.forecast == forecast

    override fun getLayoutRes() = R.layout.forecast_holder

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?)
        : FlexibleViewHolder(view, adapter) {
        val tvMax = view.tvForecastMax
        val tvMin = view.tvForecastMin
        val tvDay = view.tvForecastDay
        val icon = view.ivForecastIcon
    }
}