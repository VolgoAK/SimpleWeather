package com.volgoak.simpleweather.ui.selectCity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChanges
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.model.location.City
import com.volgoak.simpleweather.ui.selectCity.adapter.ItemCitySearchResult
import com.volgoak.simpleweather.utils.getExtraNotNull
import com.volgoak.simpleweather.utils.into
import com.volgoak.simpleweather.utils.observeSafe
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_select_city.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class SelectCityFragment: Fragment(R.layout.fragment_select_city), FlexibleAdapter.OnItemClickListener {

    companion object {
        private const val EXTRA_START_FORECAST_WHEN_SELECTED = "extra_start_forecast_when_selected"

        fun newInstance(startForecastWhenSelected: Boolean) = SelectCityFragment().apply {
            arguments = Bundle().apply {
                putBoolean(EXTRA_START_FORECAST_WHEN_SELECTED, startForecastWhenSelected)
            }
        }
    }

    private val viewModel by viewModel<SelectCityViewModel> {
        parametersOf(getExtraNotNull(EXTRA_START_FORECAST_WHEN_SELECTED, false))
    }

    private val cityAdapter = FlexibleAdapter(emptyList(), this)
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSearchCity.textChanges()
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .subscribe {
                    viewModel.searchAddress(it.toString())
                } into disposables

        with(rvSelectCity) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cityAdapter
        }

        viewModel.citiesLd.observeSafe(this, ::onSearchResult)
    }

    private fun onSearchResult(cities: List<City>) {
        cityAdapter.updateDataSet(
                cities.map { ItemCitySearchResult(it) }
        )
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return when(val item = cityAdapter.getItem(position)) {
            is ItemCitySearchResult -> {
                viewModel.onCityClicked(item.city)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
    }
}