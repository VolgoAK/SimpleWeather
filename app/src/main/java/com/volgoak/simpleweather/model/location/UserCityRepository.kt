package com.volgoak.simpleweather.model.location

import android.content.SharedPreferences
import com.google.gson.Gson
import com.volgoak.simpleweather.utils.PreferenceHelper.get
import io.reactivex.Completable
import io.reactivex.Maybe

//todo create room data base
class UserCityRepository(
        private val prefs: SharedPreferences,
        private val gson: Gson) {

    companion object {
        private const val SAVED_CITY = "saved_city"
    }

    fun getSelectedCity(): Maybe<City> {
        return Maybe.create { emitter ->
            prefs.get<String>(SAVED_CITY, null)?.let {
                emitter.onSuccess(gson.fromJson(it, City::class.java))
            } ?: run {
                emitter.onComplete()
            }
        }
    }

    fun saveSelectedCity(city: City): Completable {
        prefs.edit()
                .putString(SAVED_CITY, gson.toJson(city))
                .apply()
        return Completable.complete()
    }
}