package com.volgoak.simpleweather.di

import com.volgoak.simpleweather.ui.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { WeatherViewModel(get(), get(), get()) }
}