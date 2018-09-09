package com.zachdeibert.highprecisionvelocity.gps

import android.Manifest
import android.location.LocationManager
import android.os.Parcel
import android.os.Parcelable
import com.zachdeibert.highprecisionvelocity.R

class GPSVelocitySystem : LocationServiceVelocitySystem(LocationManager.GPS_PROVIDER, Manifest.permission.ACCESS_FINE_LOCATION) {
    override val name: Int
        get() = R.string.gps_velocity_label

    companion object CREATOR : Parcelable.Creator<GPSVelocitySystem> {
        override fun createFromParcel(parcel: Parcel): GPSVelocitySystem {
            return GPSVelocitySystem()
        }

        override fun newArray(size: Int): Array<GPSVelocitySystem?> {
            return arrayOfNulls(size)
        }
    }
}
