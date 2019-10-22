package com.pusatruq.muttabaah.util

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import android.widget.Toast

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient

import android.content.ContentValues.TAG

class GpsUtils(private val context: Context) {

    private var wayLatitude = 0.0
    private var wayLongitude = 0.0
    private val mSettingsClient: SettingsClient
    private val mLocationSettingsRequest: LocationSettingsRequest
    private val locationManager: LocationManager
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var modelLocation: ModelLocation? = null

    private val isContinue = false
    private val isGPS = false

    init {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mSettingsClient = LocationServices.getSettingsClient(context)

        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = (10 * 1000).toLong()
        locationRequest!!.fastestInterval = (2 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        mLocationSettingsRequest = builder.build()

        //**************************
        builder.setAlwaysShow(true) //this is the key ingredient
        //**************************
    }

    // method for turn on GPS
    fun turnGPSOn(onGpsListener: onGpsListener?) {

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGpsListener?.gpsStatus(true)
        } else {
            mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(context as Activity) { locationSettingsResponse ->

                    //  GPS is already enable, callback GPS status through listener
                    onGpsListener?.gpsStatus(true)
                }
                .addOnFailureListener(context) { e ->
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(context, GPS_REQUEST)
                            } catch (sie: IntentSender.SendIntentException) {
                                Log.i(TAG, "PendingIntent unable to execute request.")
                            }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage =
                                "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            Log.e(TAG, errorMessage)

                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }

    fun getLatLngLocation(mActivity: Activity): ModelLocation? {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity)
        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = (10 * 1000).toLong() // 10 seconds
        locationRequest!!.fastestInterval = (5 * 1000).toLong() // 5 seconds

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude
                        modelLocation = ModelLocation(wayLatitude, wayLongitude)
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient!!.removeLocationUpdates(locationCallback!!)
                        }
                    }
                }
            }
        }
        return modelLocation
    }

    class ModelLocation(var latitude: Double, var longitude: Double) {
        private val StreetInformation: String? = null

    }

    interface onGpsListener {
        fun gpsStatus(isGPSEnable: Boolean)
    }

    companion object {

        val LOCATION_REQUEST = 1000
        val GPS_REQUEST = 1001
    }
}