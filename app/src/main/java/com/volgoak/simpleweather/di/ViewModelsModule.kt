package com.volgoak.simpleweather.di

import com.volgoak.simpleweather.ui.forecast.ForecastViewModel
import com.volgoak.simpleweather.ui.selectCity.SelectCityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ForecastViewModel(get(), get(), get(), get()) }
    viewModel { SelectCityViewModel(get(), get()) }
}