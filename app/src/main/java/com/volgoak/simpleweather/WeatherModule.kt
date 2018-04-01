package com.volgoak.simpleweather

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.volgoak.simpleweather.model.WeatherApi
import com.volgoak.simpleweather.model.WeatherModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class WeatherModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
            retrofit.create(WeatherApi::class.java)

    @Provides
    fun provideWeahterModel(api: WeatherApi): MVP.Model = WeatherModel(api)

    @Provides
    fun providesWeatherPresenter(model: MVP.Model) : MVP.Presenter = WeatherPresenter(model)
}