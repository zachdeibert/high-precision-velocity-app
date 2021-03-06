package com.zachdeibert.highprecisionvelocity.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zachdeibert.highprecisionvelocity.IVelocityListener
import com.zachdeibert.highprecisionvelocity.IVelocitySystem
import com.zachdeibert.highprecisionvelocity.R
import com.zachdeibert.highprecisionvelocity.Velocity

class VelocityFragment : Fragment(), IVelocityListener {
    private var _system: IVelocitySystem? = null
    private var titleView: TextView? = null
    private var velocityView: TextView? = null
    private var accuracyView: TextView? = null
    val system
        get() = _system

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("VelocityFragment", "onCreateView")
        val view: View? = inflater.inflate(R.layout.fragment_velocity, container, false)
        titleView = view?.findViewById(R.id.title)
        velocityView = view?.findViewById(R.id.velocity)
        accuracyView = view?.findViewById(R.id.accuracy)
        val system: IVelocitySystem? = _system
        if (system != null) {
            titleView?.text = getString(system.name)
        }
        return view
    }

    override fun setArguments(args: Bundle?) {
        Log.i("VelocityFragment", "setArguments")
        super.setArguments(args)
        val classname: String? = args?.getString("system")
        if (classname != null) {
            val cls: Class<*> = Class.forName(classname)
            _system = cls.newInstance() as IVelocitySystem
            val system: IVelocitySystem? = _system
            if (system != null) {
                titleView?.text = getString(system.name)
            }
        }
    }

    override fun velocityChanged(velocity: Velocity) {
        Log.i("VelocityFragment", "velocityChanged")
        velocityView?.text = getString(R.string.velocity_format, velocity.velocity, getString(R.string.unit_meter_short), getString(R.string.unit_second_short))
        accuracyView?.text =
            if (velocity.accuracy.isFinite())
                getString(R.string.accuracy_format, velocity.accuracy, getString(R.string.unit_meter_short), getString(R.string.unit_second_short), velocity.confidence * 100.0)
            else
                getString(R.string.velocity_unknown)
    }

    override fun onResume() {
        Log.i("VelocityFragment", "onResume")
        super.onResume()
        system?.velocity?.addVelocityListener(this)
        system?.start(activity)
    }

    override fun onStop() {
        Log.i("VelocityFragment", "onStop")
        super.onStop()
        system?.velocity?.removeVelocityListener(this)
        system?.stop(activity)
    }

    companion object {
        @JvmStatic
        fun <T> newInstance(system: Class<T>) where T : IVelocitySystem =
                VelocityFragment().apply {
                    arguments = Bundle().apply {
                        putString("system", system.name)
                    }
                }
    }
}
