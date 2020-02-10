package com.volgoak.simpleweather.ui.container

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.singleactivityexample.navigation.MyAppNavigator
import com.example.singleactivityexample.navigation.Navigator
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.navigation.BackButtonListener
import com.volgoak.simpleweather.navigation.SelectCityScreen
import com.volgoak.simpleweather.navigation.WeatherScreen
import org.koin.android.ext.android.inject

class ContainerActivity : AppCompatActivity(R.layout.activity_container) {

    private val navigator by inject<Navigator>()

    private val navigatorHolder = MyAppNavigator(
            this,
            R.id.fragmentContainer
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            navigator.setRootScreen(SelectCityScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        navigator.setNavigator(navigatorHolder)
    }

    override fun onPause() {
        super.onPause()
        navigator.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val backHandled = (fragment as? BackButtonListener)?.onBackPressed() ?: false
        if(!backHandled) super.onBackPressed()
    }
}
