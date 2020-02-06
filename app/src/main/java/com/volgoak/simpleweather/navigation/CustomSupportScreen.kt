package com.volgoak.simpleweather.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class CustomSupportScreen: SupportAppScreen() {
    companion object {
        const val ARG_ANIMATION = "CustomSupportScreen:extra_animation"
        const val ARG_SHARED_TRANSITION_NAME_SOURCE = "CustomSupportScreen:extra_transition_name_source"
        const val ARG_SHARED_TRANSITION_NAME_DEST = "CustomSupportScreen:extra_transition_name_dest"
    }

    private var customAnimation: CustomAnimation? = null
    private var sourceSharedName: String? = null
    private var destinationSharedName: String? = null

    abstract fun createFragment(): Fragment

    override fun getFragment() = createFragment().apply { proceedExtras(this) }

    fun withCustomAnimations(@AnimatorRes @AnimRes enter: Int,
                             @AnimatorRes @AnimRes exit: Int,
                             @AnimatorRes @AnimRes popEnter: Int,
                             @AnimatorRes @AnimRes popExit: Int) {
        customAnimation = CustomAnimation(enter, exit, popEnter, popExit)
    }

    fun withSharedElement(sourceName: String, destinationName: String) {
        sourceSharedName = sourceName
        destinationSharedName = destinationName
    }

    private fun proceedExtras(fragment: Fragment) {
        if(!bundlesRequired()) return
        val args = fragment.arguments ?: Bundle().apply { fragment.arguments = this }
        customAnimation?.let { args.putParcelable(ARG_ANIMATION, it) }
        sourceSharedName?.let { args.putString(ARG_SHARED_TRANSITION_NAME_SOURCE, it)}
        destinationSharedName?.let { args.putString(ARG_SHARED_TRANSITION_NAME_DEST, it)}
    }

    private fun bundlesRequired(): Boolean =
            customAnimation != null ||
                    sourceSharedName != null ||
                    destinationSharedName != null
}

@Parcelize
data class CustomAnimation(
        @AnimatorRes @AnimRes val enter: Int,
        @AnimatorRes @AnimRes val exit: Int,
        @AnimatorRes @AnimRes val popEnter: Int,
        @AnimatorRes @AnimRes val popExit: Int
): Parcelable