package com.volgoak.simpleweather.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.volgoak.simpleweather.BuildConfig
import com.volgoak.simpleweather.MVP
import com.volgoak.simpleweather.WeatherPresenter
import com.volgoak.simpleweather.model.weather.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val NAME_OPEN_WEATHER_TOKEN = "open_weather_token_name"

val appModule = module {

    single(named(NAME_OPEN_WEATHER_TOKEN)) { BuildConfig.OPEN_WEATHER_TOKEN }

    single {
        HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Timber.i(message)})
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
    }

    single {
        Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    single { WeatherCacheProvider(get(), appId = get(named(NAME_OPEN_WEATHER_TOKEN))) }

    single<MVP.Model> {
        WeatherModel(get())
    }

    single<MVP.Presenter> {
        WeatherPresenter(get(), appContext = get())
    }
}