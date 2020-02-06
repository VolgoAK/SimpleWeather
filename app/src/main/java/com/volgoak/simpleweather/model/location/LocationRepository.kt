package com.volgoak.simpleweather.model.location

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by alex on 4/8/18.
 */
interface LocationRepository {
    fun update()
    fun getLastKnownCity(): Single<String>
    fun getLocation(): Single<Pair<Double, Double>> //todo make latlng
}