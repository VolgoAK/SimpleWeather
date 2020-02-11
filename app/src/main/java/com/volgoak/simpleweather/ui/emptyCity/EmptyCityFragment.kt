package com.volgoak.simpleweather.ui.emptyCity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.volgoak.simpleweather.R
import com.volgoak.simpleweather.utils.observeSafe
import kotlinx.android.synthetic.main.fragment_empty_city.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmptyCityFragment: Fragment(R.layout.fragment_empty_city) {

    companion object {
        private const val REQUEST_CODE_LOCATION = 1022
    }

    private val viewModel by viewModel<EmptyCityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCurentLocation.setOnClickListener {
            viewModel.onCurrentLocationClicked()
        }

        btSelectCity.setOnClickListener {
            viewModel.onSelectCityClicked()
        }

        viewModel.checkPermissionsLD.observeSafe(viewLifecycleOwner) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.onLocationPermissionGranted()
        }
    }
}