package com.volgoak.simpleweather.bean


import com.google.gson.annotations.SerializedName

data class Weather(@SerializedName("dt")
                   val dt: Int = 0,
                   @SerializedName("coord")
                   val coord: Coord = Coord(),
                   @SerializedName("visibility")
                   val visibility: Int = 0,
                   @SerializedName("weather")
                   val weather: List<WeatherItem>?,
                   @SerializedName("name")
                   val name: String = "",
                   @SerializedName("cod")
                   val cod: Int = 0,
                   @SerializedName("main")
                   val main: Main = Main(),
                   @SerializedName("clouds")
                   val clouds: Clouds = Clouds(),
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("sys")
                   val sys: Sys = Sys(),
                   @SerializedName("base")
                   val base: String = "",
                   @SerializedName("wind")
                   val wind: Wind = Wind())

data class Forecast(@SerializedName("city")
                            val city: City,
                    @SerializedName("cnt")
                            val cnt: Int = 0,
                    @SerializedName("cod")
                            val cod: String = "",
                    @SerializedName("message")
                            val message: Double = 0.0,
                    @SerializedName("list")
                            val list: List<ListItem>?)


data class Coord(@SerializedName("lon")
                 val lon: Double = 0.0,
                 @SerializedName("lat")
                 val lat: Double = 0.0)


data class Wind(@SerializedName("deg")
                val deg: Double = 0.0,
                @SerializedName("speed")
                val speed: Double = 0.0)


data class WeatherItem(@SerializedName("icon")
                       val icon: String = "",
                       @SerializedName("description")
                       val description: String = "",
                       @SerializedName("main")
                       val main: String = "",
                       @SerializedName("id")
                       val id: Int = 0)


data class Clouds(@SerializedName("all")
                  val all: Int = 0)


data class Sys(@SerializedName("country")
               val country: String = "",
               @SerializedName("sunrise")
               val sunrise: Long = 0,
               @SerializedName("sunset")
               val sunset: Long = 0,
               @SerializedName("id")
               val id: Int = 0,
               @SerializedName("type")
               val type: Int = 0,
               @SerializedName("message")
               val message: Double = 0.0)


data class Main(@SerializedName("temp")
                val temp: Double = 0.0,
                @SerializedName("temp_min")
                val tempMin: Double = 0.0,
                @SerializedName("humidity")
                val humidity: Int = 0,
                @SerializedName("pressure")
                val pressure: Double = 0.0,
                @SerializedName("temp_max")
                val tempMax: Double = 0.0)

data class ListItem(@SerializedName("dt")
                    val dt: Long = 0,
                    @SerializedName("dt_txt")
                    val dtTxt: String = "",
                    @SerializedName("weather")
                    val weather: List<WeatherItem>?,
                    @SerializedName("main")
                    val main: Main,
                    @SerializedName("clouds")
                    val clouds: Clouds,
                    @SerializedName("sys")
                    val sys: Sys,
                    @SerializedName("wind")
                    val wind: Wind)

data class City(@SerializedName("country")
                val country: String = "",
                @SerializedName("coord")
                val coord: Coord,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("id")
                val id: Int = 0)
