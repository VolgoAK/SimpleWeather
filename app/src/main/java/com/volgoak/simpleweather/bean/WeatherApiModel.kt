package com.volgoak.simpleweather.bean


import com.google.gson.annotations.SerializedName

data class Weather(
        val dt: Int = 0,
        val coord: Coord = Coord(),
        val visibility: Int = 0,
        val weather: List<WeatherItem>?,
        val name: String = "",
        val cod: Int = 0,
        val main: Main = Main(),
        val clouds: Clouds = Clouds(),
        val id: Int = 0,
        val sys: Sys = Sys(),
        val base: String = "",
        val wind: Wind = Wind())

data class Forecast(
        val city: City,
        val cnt: Int = 0,
        val cod: String = "",
        val message: Double = 0.0,
        val list: List<ListItem>?)


data class Coord(
        val lon: Double = 0.0,
        val lat: Double = 0.0)


data class Wind(
        val deg: Double = 0.0,
        val speed: Double = 0.0)


data class WeatherItem(
        val icon: String = "",
        val description: String = "",
        val main: String = "",
        val id: Int = 0)


data class Clouds(val all: Int = 0)


data class Sys(
        val country: String = "",
        val sunrise: Long = 0,
        val sunset: Long = 0,
        val id: Int = 0,
        val type: Int = 0,
        val message: Double = 0.0)


data class Main(
        val temp: Double = 0.0,
        @SerializedName("temp_min")
        val tempMin: Double = 0.0,
        val humidity: Int = 0,
        val pressure: Double = 0.0,
        @SerializedName("temp_max")
        val tempMax: Double = 0.0)

data class ListItem(
        val dt: Long = 0,
        @SerializedName("dt_txt")
        val dtTxt: String = "",
        val weather: List<WeatherItem>?,
        val main: Main,
        val clouds: Clouds,
        val sys: Sys,
        val wind: Wind)

data class City(
        val country: String = "",
        val coord: Coord,
        val name: String = "",
        val id: Int = 0)
