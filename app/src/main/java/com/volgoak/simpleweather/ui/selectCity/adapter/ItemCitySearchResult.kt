package com.volgoak.simpleweather.ui.selectCity.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.model.location.City
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_city_search_result.view.*


class ItemCitySearchResult(val city: City) : AbstractFlexibleItem<ItemCitySearchResult.ViewHolder>() {

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
                                holder: ViewHolder,
                                position: Int,
                                payloads: MutableList<Any>) {
        holder.tvCityName.text = city.name
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) = ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is ItemCitySearchResult

    override fun getLayoutRes() = R.layout.item_city_search_result

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?)
        : FlexibleViewHolder(view, adapter) {
        val tvCityName = view.tvCityName
    }
}