package com.zachdeibert.highprecisionvelocity.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.zachdeibert.highprecisionvelocity.location.FusedVelocitySystem
import com.zachdeibert.highprecisionvelocity.location.GPSVelocitySystem
import com.zachdeibert.highprecisionvelocity.location.NetworkVelocitySystem
import com.zachdeibert.highprecisionvelocity.location.PassiveVelocitySystem

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var fragments: Array<Fragment> = arrayOf(
            VelocityFragment.newInstance(GPSVelocitySystem::class.java),
            VelocityFragment.newInstance(NetworkVelocitySystem::class.java),
            VelocityFragment.newInstance(PassiveVelocitySystem::class.java),
            VelocityFragment.newInstance(FusedVelocitySystem::class.java)
    )

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
