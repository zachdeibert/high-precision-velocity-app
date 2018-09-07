package com.zachdeibert.highprecisionvelocity

class VelocityProvider {
    private var velocity: Velocity = Velocity()
    private var listeners: ArrayList<IVelocityListener> = ArrayList()

    fun addVelocityListener(listener: IVelocityListener) {
        listeners.add(listener)
    }

    fun removeVelocityListener(listener: IVelocityListener) {
        listeners.remove(listener)
    }

    fun getVelocity(): Velocity {
        return velocity
    }

    fun putVelocity(velocity: Velocity) {
        this.velocity = velocity
        for (listener in listeners) {
            listener.velocityChanged(velocity)
        }
    }
}
