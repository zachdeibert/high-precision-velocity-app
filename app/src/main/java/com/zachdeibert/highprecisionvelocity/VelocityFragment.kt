package com.zachdeibert.highprecisionvelocity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class VelocityFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_velocity, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                VelocityFragment()
    }
}