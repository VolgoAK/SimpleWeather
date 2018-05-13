package com.volgoak.simpleweather.model.location

import android.arch.lifecycle.LiveData
import android.location.Location
import com.volgoak.simpleweather.bean.City

/**
 * Created by alex on 4/8/18.
 */
interface LocationModel {
    fun update()
    fun getLastKnownCity() : LiveData<String>
    fun getLocation() : LiveData<Pair<Double, Double>>
}