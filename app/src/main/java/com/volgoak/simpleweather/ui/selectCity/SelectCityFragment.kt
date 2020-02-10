package com.volgoak.simpleweather.ui.selectCity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChanges
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.model.location.City
import com.volgoak.simpleweather.utils.observeSafe
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_select_city.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCityFragment: Fragment(R.layout.fragment_select_city), FlexibleAdapter.OnItemClickListener {

    private val viewModel by viewModel<SelectCityViewModel>()
    private val cityAdapter = FlexibleAdapter(emptyList(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSearchCity.textChanges()
                .filter { it.isNotEmpty() }
                .subscribe { viewModel.searchAddress(it.toString()) }

        with(rvSelectCity) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cityAdapter
        }

        viewModel.citiesLd.observeSafe(this, ::onSearchResult)
    }

    private fun onSearchResult(cities: List<City>) {
        cityAdapter.updateDataSet(
                cities.map { ItemCitySearcResult(it) }
        )
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}