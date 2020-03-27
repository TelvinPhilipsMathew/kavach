package com.example.kavach.ui.survey

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kavach.BaseActivity
import com.example.kavach.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.survey_activity.*
import kotlinx.android.synthetic.main.survey_activity.toolbar

class SurveyActivity : BaseActivity() {

    private lateinit var viewModel: SurveyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_activity)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        viewModel.onViewCreated(assets)

        observeCurrentIndex()
        setClickListeners()
        getLastLocation()
    }

    private fun observeCurrentIndex() {
        viewModel.currentIndex.observe(this, Observer<Int> {
            initQuestionFragment()
        })
    }

    private fun setClickListeners() {
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (mFusedLocationClient == null) {
                    requestNewLocationData(mLocationCallback)
                    return
                }
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData(mLocationCallback)
                    } else {
                        viewModel.setLatLong(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            viewModel.setLatLong(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    private fun initQuestionFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, SurveyFragment.newInstance())
            .commit()
    }

    override fun onBackPressed() {
        if (viewModel.currentIndex.value != 0) {
            viewModel.decrementCurrentIndex()
            return
        }
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}
