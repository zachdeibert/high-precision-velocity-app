package com.zachdeibert.highprecisionvelocity.location

import android.Manifest
import android.location.LocationManager
import android.os.Parcel
import android.os.Parcelable
import com.zachdeibert.highprecisionvelocity.R

class NetworkVelocitySystem : LocationServiceVelocitySystem(LocationManager.NETWORK_PROVIDER, Manifest.permission.ACCESS_COARSE_LOCATION) {
    override val name: Int
        get() = R.string.network_velocity_label

    companion object CREATOR : Parcelable.Creator<NetworkVelocitySystem> {
        override fun createFromParcel(parcel: Parcel): NetworkVelocitySystem {
            return NetworkVelocitySystem()
        }

        override fun newArray(size: Int): Array<NetworkVelocitySystem?> {
            return arrayOfNulls(size)
        }
    }
}