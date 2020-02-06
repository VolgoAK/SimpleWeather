package com.example.singleactivityexample.navigation

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.volgoak.simpleweather.navigation.CustomAnimation
import com.volgoak.simpleweather.navigation.CustomSupportScreen
import com.volgoak.simpleweather.utils.getExtra
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

open class MyAppNavigator : SupportAppNavigator{

    constructor(activity: FragmentActivity, fragmentManager: FragmentManager, containerId: Int): super(activity, fragmentManager, containerId)
    constructor(activity: FragmentActivity, containerId: Int): super(activity, containerId)

    override fun setupFragmentTransaction(
        command: Command?,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        nextFragment?.getExtra<CustomAnimation>(CustomSupportScreen.ARG_ANIMATION)?.let { anim ->
            fragmentTransaction?.setCustomAnimations(
                anim.enter, anim.exit, anim.popEnter, anim.popExit
            )
        }

        val sourceSharedElement = nextFragment?.getExtra<String>(CustomSupportScreen.ARG_SHARED_TRANSITION_NAME_SOURCE)
        val destSharedElement = nextFragment?.getExtra<String>(CustomSupportScreen.ARG_SHARED_TRANSITION_NAME_DEST)

        if(sourceSharedElement != null && destSharedElement != null) {
            val view = FrameLayout(currentFragment!!.requireContext())
            view.transitionName = sourceSharedElement
            fragmentTransaction?.addSharedElement(view, destSharedElement)
        }
    }
}