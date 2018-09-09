package com.zachdeibert.highprecisionvelocity.location

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.*
import com.zachdeibert.highprecisionvelocity.IVelocitySystem
import com.zachdeibert.highprecisionvelocity.R
import com.zachdeibert.highprecisionvelocity.Velocity
import com.zachdeibert.highprecisionvelocity.VelocityProvider
import com.zachdeibert.highprecisionvelocity.ui.MainActivity

class FusedVelocitySystem : IVelocitySystem, LocationCallback() {
    override val name: Int
        get() = R.string.fused_velocity_label
    private var _velocity: VelocityProvider = VelocityProvider()
    override val velocity: VelocityProvider
        get() = _velocity
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private fun handleLocation(location: Location) {
        Log.i("FusedVelocitySystem", "handleLocation")
        velocity.putVelocity(Velocity().apply {
            velocity = location.speed.toDouble()
            if (Build.VERSION.SDK_INT >= 26) {
                accuracy = location.speedAccuracyMetersPerSecond.toDouble()
                confidence = 68.0
            }
        })
    }

    override fun onLocationResult(location: LocationResult?) {
        if (location != null) {
            handleLocation(location.lastLocation)
        }
    }

    override fun onLocationAvailability(availability: LocationAvailability?) {
        if (availability != null) {
            if (availability.isLocationAvailable) {
                Log.i("FusedVelocitySystem", "Location is available")
            } else {
                Log.i("FusedVelocitySystem", "Location is no longer available")
            }
        }
    }

    override fun start(activity: Activity) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        this.fusedLocationClient = fusedLocationClient
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationRequest: LocationRequest = LocationRequest.create()
            locationRequest.fastestInterval = 100
            locationRequest.maxWaitTime = 1000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.smallestDisplacement = 0.0f
            fusedLocationClient.requestLocationUpdates(locationRequest, this, null)
            fusedLocationClient.lastLocation.addOnCompleteListener {
                handleLocation(it.result)
            }
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.RESTART_PERMISSION_REQUEST_CODE)
        }
    }

    override fun stop(activity: Activity) {
        val fusedLocationClient: FusedLocationProviderClient? = this.fusedLocationClient
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FusedVelocitySystem> {
        override fun createFromParcel(parcel: Parcel): FusedVelocitySystem {
            return FusedVelocitySystem()
        }

        override fun newArray(size: Int): Array<FusedVelocitySystem?> {
            return arrayOfNulls(size)
        }
    }
}
