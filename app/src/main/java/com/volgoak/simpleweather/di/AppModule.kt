package com.volgoak.simpleweather.di

import android.content.Context
import android.location.Geocoder
import com.example.singleactivityexample.navigation.Navigator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.volgoak.simpleweather.BuildConfig
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.model.location.UserCityRepository
import com.volgoak.simpleweather.model.weather.WeatherApi
import com.volgoak.simpleweather.model.weather.WeatherCacheProvider
import com.volgoak.simpleweather.model.weather.WeatherRepository
import com.volgoak.simpleweather.utils.SchedulersProvider
import com.volgoak.simpleweather.utils.SchedulersProviderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val NAME_OPEN_WEATHER_TOKEN = "open_weather_token_name"
private const val NAME_USER_CITY_PREFS = "user_city_prefs"

val appModule = module {

    single(named(NAME_OPEN_WEATHER_TOKEN)) { BuildConfig.OPEN_WEATHER_TOKEN }

    single {
        HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Timber.i(message) })
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
                .client(get())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    single { WeatherCacheProvider(get(), appId = get(named(NAME_OPEN_WEATHER_TOKEN))) }

    single {
        WeatherRepository(get())
    }

    single { Geocoder(get()) }

    single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(get<Context>()) }

    single { LocationRepository(get(), get()) }

    single<SchedulersProvider> { SchedulersProviderImpl() }

    single(named(NAME_USER_CITY_PREFS)) {
        get<Context>()
                .getSharedPreferences(NAME_USER_CITY_PREFS, Context.MODE_PRIVATE)
    }

    single {
        GsonBuilder()
                .setPrettyPrinting()
                .create()
    }

    single { UserCityRepository(get(named(NAME_USER_CITY_PREFS)), get()) }

    single { Navigator() }
}