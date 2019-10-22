package com.pusatruq.muttabaah.ui.main.maparea

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.databinding.FragmentMapareaBinding
import com.pusatruq.muttabaah.di.ActivityScoped
import com.pusatruq.muttabaah.ui.core.base.BaseFragment
import com.pusatruq.muttabaah.ui.main.comment.CommentActivity
import com.pusatruq.muttabaah.ui.main.comment.CommentFragment.Companion.NEWS_DATA
import com.pusatruq.muttabaah.ui.main.maparea.model.ModelMapsHole
import com.pusatruq.muttabaah.util.GpsUtils
import com.pusatruq.muttabaah.util.MapUtils
import javax.inject.Inject


/**
 * Created by cuongpm on 11/29/18.
 */

@ActivityScoped
class MapsAreaFragment @Inject constructor() : BaseFragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GpsUtils.onGpsListener {

    override fun gpsStatus(isGPSEnable: Boolean) {
        // turn on GPS
        isGPS = isGPSEnable
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val url = MapUtils.makeURL(
            -6.283397,
            106.896175,
            marker?.position!!.latitude,
            marker?.position!!.longitude
        )
        mapsViewModel.getDistanceLocation(url)
        return true
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mapsViewModel: MapsViewModel

    private lateinit var dataBinding: FragmentMapareaBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private var wayLatitude = 0.0
    private var wayLongitude = 0.0
    private var office_yLatitude = 0.0
    private var office_Longitude = 0.0
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var isContinue = false
    private var isGPS = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsViewModel = ViewModelProviders.of(this, viewModelFactory).get(MapsViewModel::class.java)
//        newsAdapter = NewsAdapter(ArrayList(0), mapsViewModel)
        dataBinding = FragmentMapareaBinding.inflate(inflater, container, false).apply {
            this.viewModel = mapsViewModel
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.frg) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapsViewModel.start()
        if (isAdded)
            iniLocation()
        startingLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapsViewModel.stop()
    }

    private fun handleUIEvent() {
        mapsViewModel.onNewsOpenEvent.observe(this, Observer { news ->
            news?.let {
                val intent = Intent(context, CommentActivity::class.java)
                intent.putExtra(NEWS_DATA, Gson().toJson(it))
                startActivity(intent)
            }
        })
    }

    override fun onMapReady(googlemap: GoogleMap) {
        mMap = googlemap
        // Add a marker in Sydney and move the camera
        val listMapHole: List<ModelMapsHole>

        val hole1 = LatLng(-6.282572, 106.896107)
        val hole2 = LatLng(-6.282730, 106.895544)
        placeMarkerOnMap(hole1, "Hole 1")
        placeMarkerOnMap(hole2, "Hole 2")
        mMap.setOnMarkerClickListener {
            val distance = MapUtils.distanceBetweenTwoPoint(
                wayLatitude,
                wayLongitude,
                it?.position!!.latitude,
                it?.position!!.longitude
            )
            val y: String = distance.toString()
            it.snippet = "Distance : " + y + " m"

            it.showInfoWindow()
//            val url = MapUtils.makeURL(-6.283397, 106.896175, it?.position!!.latitude, it?.position!!.longitude)
//            mapsViewModel.getDistanceLocation(url)
            return@setOnMarkerClickListener true
        }
    }

    private fun placeMarkerOnMap(location: LatLng, title: String) {
        // 1
        val markerOptions = MarkerOptions().position(location)
            .title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.golf_hole))
        // 2
        mMap.addMarker(markerOptions)
    }

    private fun iniLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create()
        locationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest?.setInterval(10 * 1000) // 10 seconds
        locationRequest?.setFastestInterval(5 * 1000) // 5 seconds

        GpsUtils(requireContext()).turnGPSOn(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult!!.getLocations()) {
                    if (location != null) {
                        wayLatitude = location!!.getLatitude()
                        wayLongitude = location!!.getLongitude()
                        val golfHalim = LatLng(wayLatitude, wayLongitude)
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
                        mMap.addMarker(
                            MarkerOptions().position(golfHalim).title("Golf").icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.golf_car)
                            )
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(golfHalim, 18f))
                        //                            ShowMessages(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));

                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient!!.removeLocationUpdates(locationCallback)
                        }
                    }
                }
            }
        }
    }

    private fun startingLocation() {
        if (!isGPS) {
            Toast.makeText(requireContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show()
            return
        }
        isContinue = false
        getLocation()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                GpsUtils.LOCATION_REQUEST
            )

        } else {
            if (isContinue) {
                mFusedLocationClient?.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            } else {
                mFusedLocationClient?.getLastLocation()!!.addOnSuccessListener(requireActivity()) { location ->
                    if (location != null) {
                        wayLatitude = location!!.getLatitude()
                        wayLongitude = location!!.getLongitude()

                        val golfHalim = LatLng(wayLatitude, wayLongitude)
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
                        mMap.addMarker(
                            MarkerOptions().position(golfHalim).title("Golf").icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.golf_car)
                            )
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(golfHalim, 18f))

                        //                        ShowMessages(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                    } else {
                        mFusedLocationClient!!.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            null
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
        LocationServices.getFusedLocationProviderClient(requireActivity())
            .removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        LocationServices.getFusedLocationProviderClient(requireActivity())
            .removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (isAdded)
            iniLocation()
            startingLocation()
    }
}