package com.volgoak.simpleweather

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.volgoak.simpleweather.model.DataBase
import com.volgoak.simpleweather.model.WeatherApi
import com.volgoak.simpleweather.model.WeatherDataBase
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
    fun provideWeahterModel(api: WeatherApi, dataBase: DataBase): MVP.Model = WeatherModel(api, dataBase)

    @Provides
    fun providesWeatherPresenter(model: MVP.Model) : MVP.Presenter = WeatherPresenter(model)

    @Provides
    fun providesDataBase(context : App) : DataBase = WeatherDataBase(context)
}