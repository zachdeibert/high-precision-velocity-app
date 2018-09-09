package com.zachdeibert.highprecisionvelocity.location

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.zachdeibert.highprecisionvelocity.IVelocitySystem
import com.zachdeibert.highprecisionvelocity.Velocity
import com.zachdeibert.highprecisionvelocity.VelocityProvider
import com.zachdeibert.highprecisionvelocity.ui.MainActivity


abstract class LocationServiceVelocitySystem(val provider: String, val permission: String) : IVelocitySystem, LocationListener {
    private var _velocity: VelocityProvider = VelocityProvider()
    override val velocity: VelocityProvider
        get() = _velocity

    override fun onLocationChanged(location: Location?) {
        Log.i("LocationServiceVelocity", "New location data collected.")
        if (location != null) {
            velocity.putVelocity(Velocity().apply {
                velocity = location.speed.toDouble()
                if (Build.VERSION.SDK_INT >= 26) {
                    accuracy = location.speedAccuracyMetersPerSecond.toDouble()
                    confidence = 68.0
                }
            })
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.i("LocationServiceVelocity", String.format("Provider '%s' changed status to '%d'.", provider, status))
    }

    override fun onProviderEnabled(provider: String?) {
        Log.i("LocationServiceVelocity", String.format("Provider '%s' enabled.", provider))
    }

    override fun onProviderDisabled(provider: String?) {
        Log.i("LocationServiceVelocity", String.format("Provider '%s' disabled.", provider))
    }

    override fun start(activity: Activity) {
        val location: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            location.requestLocationUpdates(provider, 0, 0f, this)
            Log.i("LocationServiceVelocity", "Location updates requested")
            onLocationChanged(location.getLastKnownLocation(provider))
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), MainActivity.RESTART_PERMISSION_REQUEST_CODE)
        }
    }

    override fun stop(activity: Activity) {
        Log.i("LocationServiceVelocity", "Location updates stopped")
        val location: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location.removeUpdates(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }
}
