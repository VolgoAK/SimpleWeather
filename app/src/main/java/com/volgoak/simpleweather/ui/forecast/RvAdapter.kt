package com.volgoak.simpleweather.ui.forecast

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.utils.getDayIconUrl
import java.text.SimpleDateFormat

/**
 * Created by alex on 4/8/18.
 */
class RvAdapter : RecyclerView.Adapter<ForecastHolder>() {

    private var data : List<DayForecast>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_holder, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        holder.bind(data!![position])
    }

    fun setData(data : List<DayForecast>) {
        this.data = data
        notifyDataSetChanged()
    }
}

class ForecastHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    private val tvMax: TextView = root.findViewById(R.id.tvForecastMax)
    private val tvMin: TextView = root.findViewById(R.id.tvForecastMin)
    private val tvDay: TextView = root.findViewById(R.id.tvForecastDay)
    private val icon: ImageView = root.findViewById(R.id.ivForecastIcon)

    private val dateFormat = SimpleDateFormat("EE")

    fun bind(forecast: DayForecast) {
        tvMax.text = root.context.getString(R.string.temp_format, forecast.max)
        tvMin.text = root.context.getString(R.string.temp_format, forecast.min)

        tvDay.text = dateFormat.format(forecast.date)

        Picasso.get()
                .load(getDayIconUrl(forecast.icon))
                .into(icon)
    }
}