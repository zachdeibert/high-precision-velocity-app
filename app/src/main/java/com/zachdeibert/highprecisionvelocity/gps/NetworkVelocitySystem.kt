package com.zachdeibert.highprecisionvelocity.gps

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.zachdeibert.highprecisionvelocity.IVelocitySystem
import com.zachdeibert.highprecisionvelocity.R
import com.zachdeibert.highprecisionvelocity.Velocity
import com.zachdeibert.highprecisionvelocity.VelocityProvider
import com.zachdeibert.highprecisionvelocity.ui.MainActivity

class NetworkVelocitySystem : IVelocitySystem, LocationListener {
    override val name: Int
        get() = R.string.network_velocity_label
    private var _velocity: VelocityProvider = VelocityProvider()
    override val velocity: VelocityProvider
        get() = _velocity

    override fun onLocationChanged(location: Location?) {
        Log.i("NetworkVelocitySystem", "New location data collected.")
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
        Log.i("NetworkVelocitySystem", String.format("Provider '%s' changed status to '%d'.", provider, status))
    }

    override fun onProviderEnabled(provider: String?) {
        Log.i("NetworkVelocitySystem", String.format("Provider '%s' enabled.", provider))
    }

    override fun onProviderDisabled(provider: String?) {
        Log.i("GPSVelocitySystem", String.format("Provider '%s' disabled.", provider))
    }

    override fun start(activity: Activity) {
        val location: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)
            Log.i("NetworkVelocitySystem", "Location updates requested")
            onLocationChanged(location.getLastKnownLocation(LocationManager.NETWORK_PROVIDER))
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), MainActivity.RESTART_PERMISSION_REQUEST_CODE)
        }
    }

    override fun stop(activity: Activity) {
        Log.i("NetworkVelocitySystem", "Location updates stopped")
        val location: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location.removeUpdates(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetworkVelocitySystem> {
        override fun createFromParcel(parcel: Parcel): NetworkVelocitySystem {
            return NetworkVelocitySystem()
        }

        override fun newArray(size: Int): Array<NetworkVelocitySystem?> {
            return arrayOfNulls(size)
        }
    }
}