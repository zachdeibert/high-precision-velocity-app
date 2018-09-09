package com.zachdeibert.highprecisionvelocity

import android.app.Activity
import android.os.Parcelable

interface IVelocitySystem : Parcelable {
    val name: Int
    val velocity: VelocityProvider

    fun start(activity: Activity)
    fun stop(activity: Activity)
}
