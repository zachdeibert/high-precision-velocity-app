package com.zachdeibert.highprecisionvelocity.gps

import android.Manifest
import android.location.LocationManager
import android.os.Parcel
import android.os.Parcelable
import com.zachdeibert.highprecisionvelocity.R

class PassiveVelocitySystem : LocationServiceVelocitySystem(LocationManager.PASSIVE_PROVIDER, Manifest.permission.ACCESS_FINE_LOCATION) {
    override val name: Int
        get() = R.string.passive_velocity_label

    companion object CREATOR : Parcelable.Creator<PassiveVelocitySystem> {
        override fun createFromParcel(parcel: Parcel): PassiveVelocitySystem {
            return PassiveVelocitySystem()
        }

        override fun newArray(size: Int): Array<PassiveVelocitySystem?> {
            return arrayOfNulls(size)
        }
    }
}