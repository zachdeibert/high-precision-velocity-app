package com.zachdeibert.highprecisionvelocity

class VelocityProvider {
    private var listeners: ArrayList<IVelocityListener> = ArrayList()

    fun addVelocityListener(listener: IVelocityListener) {
        listeners.add(listener)
    }

    fun removeVelocityListener(listener: IVelocityListener) {
        listeners.remove(listener)
    }

    fun putVelocity(velocity: Velocity) {
        for (listener in listeners) {
            listener.velocityChanged(velocity)
        }
    }
}
